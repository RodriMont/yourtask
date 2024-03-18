from flask import Flask, request
import pymysql
import json

db = pymysql.connect(host="127.0.0.1", port=3306, user="root", password="1234", database="yourtask", autocommit=True)
app = Flask(__name__)

cursor = db.cursor()


@app.route("/registrazione", methods=["POST"])
def registrazione_utente():
    data = request.get_json();
    #cusor = db.cursor()
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



if __name__ == "__main__":
    app.run(debug=True)