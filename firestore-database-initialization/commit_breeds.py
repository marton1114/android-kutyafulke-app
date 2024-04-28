"""
Az adott szkript egy csv fájl tartalmát tölti fel egy Google Firestore kollekcióba

A szkript specifikus funkciója egy kutyafajták adatait tartalmazó csv
tartalmának a feltöltése a "breeds" nevű Firestore kollekcióba.
A szkript a FILE_PATH-ben megadott csv fájl tartalmát alakítja át, majd
tölti fel COLLECTION_NAME nevű Firestore kollekcióba. A végrehajtáshoz
szükséges hitelesítő json fájl elérését a CREDENTIALS_PATH változóban
kell meghatározni, a végrehajtáskor alkalmazott "batch" méretét pedig
a BATCH_SIZE változóban adhatjuk meg.
"""

import csv
from firebase_admin import credentials, firestore, initialize_app

FILE_PATH = 'breeds.csv'
CREDENTIALS_PATH = './adminsdk.json'
COLLECTION_NAME = 'breeds'
BATCH_SIZE = 500

cred = credentials.Certificate(CREDENTIALS_PATH)
app = initialize_app(cred)
store = firestore.client()

with open(FILE_PATH, 'r', encoding='utf-8') as csv_file:
    reader = csv.DictReader(csv_file)
    reader_list = list(reader)

    for i in range(0, len(reader_list), BATCH_SIZE):
        batch = store.batch()
        batched = reader_list[i:i+BATCH_SIZE]

        for single in batched:
            doc = store.collection(COLLECTION_NAME).document(single['id'])
            batch.set(doc, single)

        batch.commit()
