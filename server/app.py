from flask import Flask, request
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

@app.route("/progetti_utente")
def progetti_utente():
    id_utente = request.args.get('id')

    cursor.execute(f"""select progetto.id, progetto.nome_progetto, progetto.data_avvio, progetto.data_scadenza, progetto.budget
                    from utenteprogetto
                    inner join progetto
                    on utenteprogetto.id_progetto = progetto.id
                    where utenteprogetto.id_utente = {id_utente}""")
    
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
def utente_email():
    email = request.args.get('email')

    cursor.execute(f"""select id, username, email, password
                       from utente
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
def task_utente():
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

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)