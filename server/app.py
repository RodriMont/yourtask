from flask import Flask, jsonify, request
from flask_cors import CORS
import pymysql
import json
from model import Progetto, Task, Utente

db = pymysql.connect(host="rest-api.clweu6iamvqi.eu-north-1.rds.amazonaws.com", 
                     port=3306, user="rodri", 
                     password="12345678", 
                     database="yourtask", 
                     autocommit=True)

app = Flask(__name__)
CORS(app)
connection = db.cursor()


#=================================================================================================================================
# GET
#=================================================================================================================================

@app.route("/progetti_utente")
def progetti_utente():
    id_utente = request.args.get('id')

    connection.execute(f"""select progetto.id, progetto.nome_progetto, progetto.data_avvio, progetto.data_scadenza, progetto.budget
                    from utenteprogetto
                    inner join progetto
                    on utenteprogetto.id_progetto = progetto.id
                    where utenteprogetto.id_utente = {id_utente}""")
    
    rows = connection.fetchall()
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

@app.route("/utente_email")
def get_utente_email():
    email = request.args.get('email')

    connection.execute(f"""select id, username, email, password
                       from utente
                       where email = \"{email}\"""")
    
    rows = connection.fetchall()
    utenti = []

    for row in rows:
        id = row[0]
        username = row[1]
        email = row[2]
        password = row[3]

        utente = Utente(id, username, email, password)
        utenti.append(utente.__dict__)

    return json.dumps(utenti)

@app.route("/task_utente")
def get_task_utente():
    id_utente = request.args.get('id_utente')
    id_progetto = request.args.get('id_progetto')

    connection.execute(f"""select task.id, task.nome_task, task.data_avvio, task.data_scadenza, task.priorita, task.id_progetto
                       from taskutente
                        inner join task
                        on task.id = taskutente.id_task
                        where id_utente = {id_utente} and task.id_progetto = {id_progetto}""")

    rows = connection.fetchall()
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


@app.route("/utenti_task")
def get_utenti_task():
    id_task = request.args.get('id_task')
    id_progetto = request.args.get('id_progetto')

    connection.execute(f"""select utente.id, utente.username, utente.email, utente.password
                       from taskutente
                       inner join utente
                       on taskutente.id_utente = utente.id
                       inner join task
                       on taskutente.id_task = task.id
                       where taskutente.id_task = {id_task} and task.id_progetto = {id_progetto}""")

    rows = connection.fetchall()
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
            query = "INSERT INTO utente(username, email, password) VALUES (%s, %s, %s )"
            connection.execute(query, (username, email, password))

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
    connection.execute(query, (email))
    user = connection.fetchall()
    return user



@app.route("/utenti/<int:id>", methods=["DELETE"])
def delete_user(id):
    try:
        sql = "delete from utente where id = %s"
        connection.execute(sql, (id))
        db.commit()

        return jsonify({"message": "Utente eliminato con successo", "code": 200})
    except Exception as e:
        connection.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione dell'utente", "code": 500})
    
@app.route("/progetti/<int:id>", methods=["DELETE"])
def delete_progetto(id):
    try:
        sql = "delete from progetto where id = %s"
        connection.execute(sql, (id))
        db.commit()

        return jsonify({"message": "Progetto eliminato con successo", "code": 200})
    except Exception as e:
        connection.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del progetto", "code": 500})



#UPDATE
@app.route('/update_user/<int:id>', methods = ['PUT'])
def update_user(id):
    try:
        data = request.json
        sql = "UPDATE utente SET username = %s, email = %s where id= %s"
        connection.execute(sql, (data['username'] , data['email'] , id))
        db.commit()
    
        return jsonify ({'message': 'Data updated successfully' })
    except Exception as e : 
        db.rollback()
        print(e.__str__())
        return jsonify ({'error' : str(e)})


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
    
