from flask import Flask, jsonify, request
from flask_cors import CORS
import pymysql
import json
from model import *

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

# Ritorna tutti i task che l'utente deve svolgere all'interno di un progetto, dato il suo id e l'id del progetto
@app.route("/task_utente")
def get_task_utente():
    id_utente = request.args.get('id_utente')
    id_progetto = request.args.get('id_progetto')

    db.execute(f"""select task.id, task.nome_task, task.data_avvio, task.data_scadenza, task.priorita, task.id_progetto
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
    id_progetto = request.args.get('id_progetto')

    cursor.execute(f"""select utenti.id, utenti.username, utenti.email, utenti.password
                       from taskutente
                       inner join utenti
                       on taskutente.id_utente = utenti.id
                       inner join task
                       on taskutente.id_task = task.id
                       where taskutente.id_task = {id_task} and task.id_progetto = {id_progetto}""")

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

#=================================================================================================================================
# POST
#=================================================================================================================================    

@app.route("/registrazione", methods=["POST"])
def registrazione_utente():
    data = request.get_json()
    username = data["username"]
    email = data["email"]
    password = data["password"]

    res = {
        "ok": False,
    }

    try:
        user = get_user_by_email(email)

        
        if(len(user) == 0):
            query = "INSERT INTO utente(username, email, password) VALUES (%s, %s, %s)"
            cursor.execute(query, (username, email, password))

            res["ok"] = True
            res["msg"] = "Usuario Creato "
            db.commit()
        else:
            res["ok"] = False
            res["msg"] = "Usuario non creato, email usato"

    except Exception as e:
        print(e)
        res["ok"] = False
        res["msg"] = f"Errore server: {e}"

    return json.dumps(res)

@app.route("/login", methods=["POST"])
def login():
    data = request.get_json()
    email = data["email"]
    password = data["password"]

    res = {
        "ok": False
    }

    try:
        utente_db = get_user_by_email(email)
        if(len(utente_db) == 0):
            res["ok"] = False
        else:
            utente_db = utente_db[0]
            if(utente_db[3] == password):
                res["ok"] = True


    except Exception as e:
        print(f"Error: {e}")

    return res

def get_user_by_email(email:str):
    query = "SELECT * FROM utente WHERE email= %s"
    cursor.execute(query, (email))
    user = cursor.fetchall()
    return user

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

        return jsonify({"message": "Utente eliminato con successo", "code": 200})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione dell'utente", "code": 500})
    
# Cancella un progetto, dato il suo id
@app.route("/progetti/<int:id>", methods=["DELETE"])
def delete_progetto(id):
    try:
        with db.cursor() as cursor:
            sql = "delete from progetti where id = %s"
            cursor.execute(sql, (id))

        db.commit()

        return jsonify({"message": "Progetto eliminato con successo", "code": 200})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del progetto", "code": 500})

# Cancella un task, dato il suo id
@app.route("/task/<int:id>", methods = ["DELETE"])
def delete_task(id):
    try:
        with db.cursor() as cursor:
            sql = "delete from task where id = %s"
            cursor.execute(sql, (id))

        db.commit()

        return jsonify({"message": "Task eliminato con successo", "code": 200})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del task", "code": 500})
    
# Cancella un ruolo, dato il suo id
@app.route("/ruoli/<int:id>", methods = ["DELETE"])
def delete_ruolo(id):
    try:
        with db.cursor() as cursor:
            sql = "delete from ruoli where id = %s"
            cursor.execute(sql, (id))

        db.commit()

        return jsonify({"message": "Ruolo eliminato con successo", "code": 200})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del ruolo", "code": 500})

#=================================================================================================================================
# PUT
#================================================================================================================================= 

# Modifica le informazioni di un utente, dato il suo id
@app.route("/utenti/<int:id>", methods = ["PUT"])
def modifica_utente(id):
    data = request.json

    if data["username"] != None:
        set_query = "password = %s"
    elif data["password"] != None:
        set_query = "username = %s"

    try:
        with db.cursor() as cursor:
            sql = f"update utenti set {set_query} where id = %s"
            cursor.execute(sql, (data["username"], data["password"], id))

        db.commit()

        return jsonify({"message": "Utente modificato con successo", "code": 200})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella modifica dell'utente", "code": 500})

# Modifica un progetto, dato il suo id
@app.route("/progetti/<int:id>", methods = ["PUT"])
def modifica_progetto(id):
    data = request.json

    try:
        with db.cursor() as cursor:
            sql = "update progetti set nome_progetto = %s, data_avvio = %s, data_scadenza = %s, budget = %s where id = %s"
            cursor.execute(sql, (data["nome_progetto"], data["data_avvio"], data["data_scadenza"], data["budget"], id))

        db.commit()

        return jsonify({"message": "Progetto modificato con successo", "code": 200})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella modifica del progetto", "code": 500})

# Modifica un task, dato il suo id
@app.route("/task/<int:id>", methods = ["PUT"])
def modifica_task(id):
    data = request.json

    try:
        with db.cursor() as cursor:
            sql = "update task set nome_task = %s, data_avvio = %s, data_scadenza = %s, priorita = %s where id = %s"
            cursor.execute(sql, (data["nome_task"], data["data_avvio"], data["data_scadenza"], data["priorita"], id))

        db.commit()

        return jsonify({"message": "Task modificato con successo", "code": 200})
    except Exception as e:
        db.rollback()
        return jsonify({"message:": "Errore nella modifica del task", "code": 500})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)