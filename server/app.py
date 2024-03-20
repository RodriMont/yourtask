from flask import Flask, request, jsonify
import pymysql
import json
from model import *

app = Flask(__name__)

connection = pymysql.connect(
    host="localhost",
    user="root",
    password="root",
    database="yourtask",
    port=3306
)

cursor = connection.cursor()

@app.route('/')
def index():
    return "Hello World!"

#=================================================================================================================================
# GET
#=================================================================================================================================

@app.route("/progetti_utente")
def progetti_utente():
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

@app.route("/utente_email")
def get_utente_email():
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
# DELETE
#=================================================================================================================================    

@app.route("/utenti/<int:id>", methods = ["DELETE"])
def delete_user(id):
    try:
        with connection.cursor() as cursor:
            sql = "delete from utenti where id = %s"
            cursor.execute(sql, (id))

        connection.commit()

        return jsonify({"message": "Utente eliminato con successo", "code": 200})
    except Exception as e:
        connection.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione dell'utente", "code": 500})
    
@app.route("/progetti/<int:id>", methods=["DELETE"])
def delete_progetto(id):
    try:
        with connection.cursor() as cursor:
            sql = "delete from progetti where id = %s"
            cursor.execute(sql, (id))

        connection.commit()

        return jsonify({"message": "Progetto eliminato con successo", "code": 200})
    except Exception as e:
        connection.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del progetto", "code": 500})

@app.route("/task/<int:id>", methods = ["DELETE"])
def delete_task(id):
    try:
        with connection.cursor() as cursor:
            sql = "delete from task where id = %s"
            cursor.execute(sql, (id))

        connection.commit()

        return jsonify({"message": "Task eliminato con successo", "code": 200})
    except Exception as e:
        connection.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del task", "code": 500})
    
@app.route("/ruoli/<int:id>", methods = ["DELETE"])
def delete_ruolo(id):
    try:
        with connection.cursor() as cursor:
            sql = "delete from ruoli where id = %s"
            cursor.execute(sql, (id))

        connection.commit()

        return jsonify({"message": "Ruolo eliminato con successo", "code": 200})
    except Exception as e:
        connection.rollback()
        return jsonify({"message:": "Errore nell'eliminiazione del ruolo", "code": 500})

#=================================================================================================================================
# PUT
#================================================================================================================================= 

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)