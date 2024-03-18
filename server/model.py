class Progetto:
    id = None
    nome_progetto = None
    data_avvio = None
    data_scadenza = None
    budget = None

    def __init__(self, id, nome_progetto, data_avvio, data_scadenza, budget):
        self.id = id
        self.nome_progetto = nome_progetto
        self.data_avvio = data_avvio
        self.data_scadenza = data_scadenza
        self.budget = budget

class Utente:
    id = None
    username = None
    email = None
    password = None

    def __init__(self, id, username, email, password):
        self.id = id
        self.username = username
        self.email = email
        self.password = password

class Ruolo:
    id = None
    nome_ruolo = None
    id_task = None

    def __init__(self, id, nome_ruolo, id_task):
        self.id = id
        self.nome_ruolo = nome_ruolo
        self.id_task = id_task

class Task:
    id = None
    nome_task = None
    data_avvio = None
    data_scadenza = None
    priorita = None
    id_progetto = None

    def __init__(self, id, nome_task, data_avvio, data_scadenza, priorita, id_progetto):
        self.id = id
        self.nome_task = nome_task
        self.data_avvio = data_avvio
        self.data_scadenza = data_scadenza
        self.priorita = priorita

class RuoloUtente:
    id_ruolo = None
    id_utente = None

    def __init__(self, id_ruolo, id_utente):
        self.id_ruolo = id_ruolo
        self.id_utente = id_utente

class ProgettiUtente:
    id_utente = None
    id_progetto = None

    def __init__(self, id_utente, id_progetto):
        self.id_utente = id_utente
        self.id_progetto = id_progetto

class TaskUtente:
    id_utente = None
    id_task = None

    def __init__(self, id_utente, id_task):
        self.id_utente = id_utente
        self.id_progetto = id_task

class Lavori:
    id_utente = None
    id_progetto = None
    id_task = None
    id_ruolo = None

    def __init__(self, id_utente, id_progetto, id_task, id_ruolo):
        self.id_utente = id_utente
        self.id_progetto = id_progetto
        self.id_task = id_task
        self.id_ruolo = id_ruolo