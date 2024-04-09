from flask import Flask, jsonify, request
from flask_cors import CORS
import pymysql
import json
from model import *

##ciao

db = pymysql.connect(host="rest-api.clweu6iamvqi.eu-north-1.rds.amazonaws.com", 
                    port=3306, user="rodri", 
                    password="12345678", 
                    database="yourtask", 
                    autocommit=True)

app = Flask(__name__)
CORS(app)
cursor = db.cursor()

#=================================================================================================================================
# GET
#=================================================================================================================================

# Ritorna tutti i progetti a cui sta partecipando l'utente, dato il suo id
@app.route("/progetti_utente")
def get_progetti_utente():
    id_utente = request.args.get('id')

    cursor.execute(f"""select progetti.id, progetti.nome_progetto, progetti.data_avvio, progetti.data_scadenza, progetti.budget
                    from progettiutente
                    inner join progetti
                    on progettiutente.id_progetto = progetti.id
                    where progettiutente.id_utente = {id_utente}""")
    
    rows = cursor.fetchall()
    progetti = []

    for row in rows:
        id = row[0]
        nome_progetto = row[1]
        data_avvio = row[2]
        data_scadenza = row[3]
        budget = row[4]

        progetto = Progetto(id, nome_progetto, str(data_avvio), str(data_scadenza), budget)
        progetti.append(progetto.__dict__)

    return json.dumps(progetti)

# Ritorna le informazioni di un utente data la sua email
@app.route("/utente")
def get_utente():
    email = request.args.get('email')

    cursor.execute(f"""select id, username, email, password
                       from utenti
                       where email = \"{email}\"""")
    
    rows = cursor.fetchall()
    utenti = []

    for row in rows:
        id = row[0]
        username = row[1]
        email = row[2]
        password = row[3]

        utente = Utente(id, username, email, password)
        utenti.append(utente.__dict__)

    return json.dumps(utenti)

# Ritorna le informazioni di tanti utenti date le email
@app.route("/utenti")
def get_utenti():
    emails = request.args.getlist('email')
    
    where = ""

    for i in range(0, len(emails)):
        where += f"email = \"{emails[i]}\""

        if i + 1 < len(emails):
            where += " or "

    cursor.execute(f"""select id, username, email, password
                       from utenti
                       where {where}""")
    
    rows = cursor.fetchall()
    utenti = []

    for row in rows:
        id = row[0]
        username = row[1]
        email = row[2]
        password = row[3]

        utente = Utente(id, username, email, password)
        utenti.append(utente.__dict__)

    return json.dumps(utenti)

# Ritorna tutti i task che l'utente deve svolgere all'interno di un progetto, dato il suo id e l'id del progetto
@app.route("/task_utente")
def get_task_utente():
    id_utente = request.args.get('id_utente')
    id_progetto = request.args.get('id_progetto')

    cursor.execute(f"""select task.id, task.nome_task, task.data_avvio, task.data_scadenza, task.priorita, task.id_progetto
                       from taskutente
                        inner join task
                        on task.id = taskutente.id_task
                        where id_utente = {id_utente} and task.id_progetto = {id_progetto}""")

    rows = cursor.fetchall()
    tasks = []

    for row in rows:
        id = row[0]
        nome_task = row[1]
        data_avvio = row[2]
        data_scadenza = row[3]
        priorita = row[4]
        id_progetto = row[5]

        task = Task(id, nome_task, str(data_avvio), str(data_scadenza), priorita, id_progetto)
        tasks.append(task.__dict__)

    return json.dumps(tasks)

# Ritorna tutti gli utenti a cui è stato assegnato un determinato task, dato il suo id e l'id del progetto
@app.route("/utenti_task")
def get_utenti_task():
    id_task = request.args.get('id_task')

    cursor.execute(f"""select utenti.id, utenti.username, utenti.email, utenti.password
                       from taskutente
                       inner join utenti
                       on taskutente.id_utente = utenti.id
                       where taskutente.id_task = {id_task}""")

    rows = cursor.fetchall()
    utenti = []

    for row in rows:
        id = row[0]
        username = row[1]
        email = row[2]
        password = row[3]

        utente = Utente(id, username, email, password)
        utenti.append(utente.__dict__)

    return json.dumps(utenti)

# Ritorna tutti gli utenti che partecipano in un determinato progetto, dato il suo id e l'id del progetto
@app.route("/utenti_progetto")
def get_utenti_progetto():
    id_progetto = request.args.get('id_progetto')

    cursor.execute(f"""select utenti.id, utenti.username, utenti.email, utenti.password
                       from progettiutente
                       inner join utenti
                       on progettiutente.id_utente = utenti.id
                       inner join progetti
                       on progettiutente.id_progetto = progetti.id
                       where progettiutente.id_progetto = {id_progetto}""")

    rows = cursor.fetchall()
    utenti = []

    for row in rows:
        id = row[0]
        username = row[1]
        email = row[2]
        password = row[3]

        utente = Utente(id, username, email, password)
        utenti.append(utente.__dict__)

    return json.dumps(utenti)

@app.route("/ruolo_utente")
def get_ruolo_utente():
    id_utente = request.args.get('id_utente')
    id_progetto = request.args.get('id_progetto')

    cursor.execute(f"""select ruoli.id, ruoli.nome_ruolo, ruoli.colore, ruoli.id_progetto
                       from ruoloutente
                       inner join ruoli
                       on ruoloutente.id_ruolo = ruoli.id
                       inner join progetti
                       on ruoli.id_progetto = progetti.id
                       where ruoloutente.id_utente = {id_utente} and ruoli.id_progetto = {id_progetto}""")

    rows = cursor.fetchall()
    ruoli = []

    for row in rows:
        id_ruolo = row[0]
        nome_ruolo = row[1]
        colore = row[2]
        id_progetto = row[3]

        ruolo = Ruolo(id_ruolo, nome_ruolo, colore, id_progetto)
        ruoli.append(ruolo.__dict__)

    return json.dumps(ruoli)

@app.route("/lavori")
def get_lavori():
    id_utente = request.args.get("id_utente")
    id_progetto = request.args.get("id_progetto")
    id_ruolo = request.args.get("id_ruolo")

    cursor.execute(f"""select id_utente, id_progetto, id_task, id_ruolo
                       from lavori
                       where id_utente = {id_utente} and id_progetto = {id_progetto} and id_ruolo = {id_ruolo}""")

    rows = cursor.fetchall()
    lavori = []

    for row in rows:
        id_utente_ = row[0]
        id_progetto_ = row[1]
        id_task_ = row[2]
        id_ruolo_ = row[3]

        lavoro = Lavoro(id_utente_, id_progetto_, id_task_, id_ruolo_)
        lavori.append(lavoro.__dict__)

    return json.dumps(lavori)

#=================================================================================================================================
# POST
#=================================================================================================================================    

@app.route("/utenti", methods = ["POST"])
def post_utente():
    data = request.json    

    try:
        with db.cursor() as cursor:
            sql = """insert into utenti(username, email, password)
                     values (%s, %s, %s)"""
            cursor.execute(sql, (data["username"], data["email"], data["password"]))

        db.commit()

        return jsonify({"message": "Utente creato con successo", "code": 200, "id": cursor.lastrowid}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella creazione dell'utente", "code": 400, "id": -1}), 200

@app.route("/registrazione", methods=["POST"])
def registrazione_utente():
    data = request.get_json()
    username = data["username"]
    email = data["email"]
    password = data["password"]

    print(email)

    res = {
        "ok": False,
    }

    try:
        user = get_user_by_email(email)
        
        if len(user) == 0:
            query = "INSERT INTO utenti(username, email,  password) VALUES (%s, %s, %s)"
            cursor.execute(query, (username, email, password))

            res["ok"] = True
            res["message"] = "Utente creato "
            res["code"] = 200
            db.commit()
        else:
            res["ok"] = False
            res["message"] = "Utente non creato, email già in utilizzo"
            res["code"] = 200
            return json.dumps(res), 400

    except Exception as e:
        print(e)
        res["ok"] = False
        res["message"] = f"Errore server: {e}"
        res["code"] = 500
        return json.dumps(res), 500

    return json.dumps(res), 200

@app.route("/login", methods=["POST"])
def login():
    data = request.get_json()
    email = data["email"]
    password = data["password"]

    res = {
        "message": "",
        "code": 400,
        "auth": False,
        "id": -1
    }

    try:
        utente_db = get_user_by_email(email)
        if(len(utente_db) == 0):
            res["message"] = "Utente non trovato"
            res["code"] = 200
            res["auth"] = False
        else:
            utente_db = utente_db[0]
            
            if(utente_db[3] == password):
                res["code"] = 200
                res["message"] = "Password corretta"
                res["auth"] = True
                res["id"] = utente_db[0]
                
            else:
                res["code"] = 200
                res["message"] = "Password non corretta",
                res["auth"] = True

    except Exception as e:
        print(f"Error: {e}")
        res["auth"] = False
        res["code"] = 500
        res["message"] = "Server error"

    return res


def get_user_by_email(email:str):
    query = "SELECT * FROM utenti WHERE email= %s"
    cursor.execute(query, (email))
    user = cursor.fetchall()
    return user



# Crea un nuovo progetto
@app.route("/progetti", methods = ["POST"])
def post_progetto():
    data = request.json    

    try:
        with db.cursor() as cursor:
            sql = """insert into progetti(nome_progetto, data_avvio, data_scadenza, budget)
                     values (%s, %s, %s, %s)"""
            cursor.execute(sql, (data["nome_progetto"], data["data_avvio"], data["data_scadenza"], data["budget"]))

        db.commit()

        return jsonify({"message": "Progetto creato con successo", "code": 200, "id": cursor.lastrowid})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella creazione del progetto", "code": 400, "id": -1})

# Crea un nuovo task
@app.route("/task", methods = ["POST"])
def post_task():
    data = request.json    

    try:
        with db.cursor() as cursor:
            sql = """insert into task(nome_task, data_avvio, data_scadenza, priorita, id_progetto)
                     values (%s, %s, %s, %s, %s)"""
            cursor.execute(sql, (data["nome_task"], data["data_avvio"], data["data_scadenza"], data["priorita"], data["id_progetto"]))

        db.commit()

        return jsonify({"message": "Task creato con successo", "code": 200, "id": cursor.lastrowid}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella creazione del task", "code": 400, "id": -1}), 200

# Crea un nuovo ruolo
@app.route("/ruoli", methods = ["POST"])
def post_ruolo():
    data = request.json    

    try:
        with db.cursor() as cursor:
            sql = """insert into ruoli(nome_ruolo, colore, id_progetto)
                     values (%s, %s, %s)"""
            cursor.execute(sql, (data["nome_ruolo"], data["colore"], data["id_progetto"]))

        db.commit()

        return jsonify({"message": "Ruolo creato con successo", "code": 200, "id": cursor.lastrowid})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella creazione del ruolo", "code": 400, "id": -1})
    
#aggiunge utenti in un progetto
@app.route("/utenti_progetto", methods = ["POST"])
def post_utenti_progetto():
    data = request.json

    values = ""

    for i in range(0, len(data)):
        values += f"({data[i]["id_utente"]}, {data[i]["id_progetto"]})"

        if i + 1 < len(data):
            values += ","

    try:
        with db.cursor() as cursor:
            sql = f"""insert into progettiutente(id_utente, id_progetto)
                     values {values}"""
            cursor.execute(sql)

        db.commit()

        return jsonify({"message": "Utenti aggiunti con successo al progetto", "code": 200, "id": 0}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'aggiunta degli utenti al progetto", "code": 400, "id": -1}), 200
    
#aggiunge utenti in un task di un progetto
@app.route("/utenti_task", methods = ["POST"])
def post_utenti_task():
    data = request.json

    values = ""

    for i in range(0, len(data)):
        values += f"({data[i]["id_utente"]}, {data[i]["id_task"]})"

        if i + 1 < len(data):
            values += ","

    try:
        with db.cursor() as cursor:
            sql = f"""insert into taskutente(id_utente, id_task)
                     values {values}"""
            cursor.execute(sql)

        db.commit()

        return jsonify({"message": "Utenti aggiunti con successo al task", "code": 200, "id": 0}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'aggiunta degli utenti al task", "code": 400, "id": -1}), 200 

# Crea un lavoro
@app.route("/lavori", methods = ["POST"])
def post_lavoro():
    data = request.json    

    try:
        with db.cursor() as cursor:
            sql = """insert into lavori(id_utente, id_progetto, id_task, id_ruolo)
                     values (%s, %s, %s, %s)"""
            cursor.execute(sql, (data["id_utente"], data["id_progetto"], data["id_task"], data["id_ruolo"]))

        db.commit()

        return jsonify({"message": "Lavoro creato con successo", "code": 200, "id": 0})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella creazione del lavoro", "code": 400, "id": -1})
    
#=================================================================================================================================
# DELETE
#=================================================================================================================================    

# Cancella un utente, dato il suo id
@app.route("/utenti/<int:id>", methods = ["DELETE"])
def delete_user(id):
    try:
        with db.cursor() as cursor:
            sql = "delete from utenti where id = %s"
            cursor.execute(sql, (id))

        db.commit()

        return jsonify({"message": "Utente eliminato con successo", "code": 200, "id": id}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione dell'utente", "code": 400, "id": -1}), 200
    
# Cancella un progetto, dato il suo id
@app.route("/progetti/<int:id>", methods=["DELETE"])
def delete_progetto(id):
    try:
        with db.cursor() as cursor:
            sql = "delete from progetti where id = %s"
            cursor.execute(sql, (id))

        db.commit()

        return jsonify({"message": "Progetto eliminato con successo", "code": 200, "id": id}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del progetto", "code": 400, "id": -1}), 200

# Cancella un task, dato il suo id
@app.route("/task/<int:id>", methods = ["DELETE"])
def delete_task(id):
    try:
        with db.cursor() as cursor:
            sql = "delete from task where id = %s"
            cursor.execute(sql, (id))

        db.commit()

        return jsonify({"message": "Task eliminato con successo", "code": 200, "id": id}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del task", "code": 400, "id": -1}), 200
    
# Cancella un ruolo, dato il suo id
@app.route("/ruoli/<int:id>", methods = ["DELETE"])
def delete_ruolo(id):
    try:
        with db.cursor() as cursor:
            sql = "delete from ruoli where id = %s"
            cursor.execute(sql, (id))

        db.commit()

        return jsonify({"message": "Ruolo eliminato con successo", "code": 200, "id": id}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del ruolo", "code": 400, "id": -1}), 200

# Rimuove un utente da un progetto, dato l'id del progetto e l'id dell'utente
@app.route("/utente_progetto/<int:id_utente>/<int:id_progetto>", methods = ["DELETE"])
def delete_utente_progetto(id_utente, id_progetto):
    try:
        with db.cursor() as cursor:
            sql = "delete from progettiutente where id_utente = %s and id_progetto = %s"
            cursor.execute(sql, (id_utente, id_progetto))

        db.commit()

        return jsonify({"message": "Utente rimosso dal progetto con successo", "code": 200, "id": id_utente}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella rimozione dell'utente dal progetto", "code": 400, "id": -1}), 200

# Rimuove un utente da un task, dato l'id del task e l'id dell'utente
@app.route("/utente_task/<int:id_utente>/<int:id_task>", methods = ["DELETE"])
def delete_utente_task(id_utente, id_task):
    try:
        with db.cursor() as cursor:
            sql = "delete from taskutente where id_utente = %s and id_task = %s"
            cursor.execute(sql, (id_utente, id_task))

        db.commit()

        return jsonify({"message": "Utente rimosso dal task con successo", "code": 200, "id": id_utente}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella rimozione dell'utente dal task", "code": 400, "id": -1}), 200

#=================================================================================================================================
# PUT
#================================================================================================================================= 

# Modifica le informazioni di un utente, dato il suo id
@app.route("/utenti/<int:id>", methods = ["PUT"])
def modifica_utente(id):
    data = request.json

    try:
        with db.cursor() as cursor:
            sql = f"update utenti set username = %s where id = %s"
            cursor.execute(sql, (data["username"], id))

        db.commit()

        return jsonify({"message": "Utente modificato con successo", "code": 200, "id": id}), 200
    except Exception as e:
        db.rollback()
        print(str(e))
        return jsonify({"message:": "Errore nella modifica dell'utente", "code": 400, "id": -1}), 200

# Modifica un progetto, dato il suo id
@app.route("/progetti/<int:id>", methods = ["PUT"])
def modifica_progetto(id):
    data = request.json

    try:
        with db.cursor() as cursor:
            sql = "update progetti set nome_progetto = %s, data_avvio = %s, data_scadenza = %s, budget = %s where id = %s"
            cursor.execute(sql, (data["nome_progetto"], data["data_avvio"], data["data_scadenza"], data["budget"], id))

        db.commit()

        return jsonify({"message": "Progetto modificato con successo", "code": 200, "id": id}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella modifica del progetto", "code": 400, "id": -1}), 200

# Modifica un task, dato il suo id
@app.route("/task/<int:id>", methods = ["PUT"])
def modifica_task(id):
    data = request.json

    try:
        with db.cursor() as cursor:
            sql = "update task set nome_task = %s, data_avvio = %s, data_scadenza = %s, priorita = %s where id = %s"
            cursor.execute(sql, (data["nome_task"], data["data_avvio"], data["data_scadenza"], data["priorita"], id))

        db.commit()

        return jsonify({"message": "Task modificato con successo", "code": 200, "id": id}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella modifica del task", "code": 400, "id": -1}), 200
    
@app.route("/ruoli/<int:id>", methods = ["PUT"])
def modifica_ruolo(id):
    data = request.json

    try:
        with db.cursor() as cursor:
            sql = "update ruoli set nome_ruolo = %s, colore = %s where id = %s"
            cursor.execute(sql, (data["nome_ruolo"], data["colore"], id))
        
        db.commit()

        return jsonify({"message": "Ruolo modificato con successo", "code": 200, "id": id}), 200
    except Exception as e:
        db.rollback()
        return jsonify({"message": "Errore nella modifica del ruolo", "code": 400, "id": -1}), 200
    
    


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
    
