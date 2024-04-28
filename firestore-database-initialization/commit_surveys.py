"""
Az adott szkript egy csv fájl tartalmát tölti fel egy Google Firestore kollekcióba

A szkript specifikus funkciója egy kérdőív szűrt adatainak a feltöltése a
"surveys" nevű Firestore kollekcióba.
A szkript a FILE_PATH-ben megadott csv fájl tartalmát alakítja át, majd
tölti fel COLLECTION_NAME nevű Firestore kollekcióba. A végrehajtáshoz
szükséges hitelesítő json fájl elérését a CREDENTIALS_PATH változóban
kell meghatározni, a végrehajtáskor alkalmazott "batch" méretét pedig
a BATCH_SIZE változóban adhatjuk meg.
"""

import csv
from firebase_admin import credentials, firestore, initialize_app

FILE_PATH = 'filtered-data.csv'
CREDENTIALS_PATH = './adminsdk.json'
COLLECTION_NAME = 'surveys'
BATCH_SIZE = 500

cred = credentials.Certificate(CREDENTIALS_PATH)
app = initialize_app(cred)
store = firestore.client()

dict_list = []
with open(FILE_PATH, 'r', encoding='utf-8') as csvfile:
    csv_reader = csv.DictReader(csvfile)

    headers = csv_reader.fieldnames

    for line in csv_reader:
        for header in headers:
            if header == 'colors':
                coat_list = line[header].split(';')
                line[header] = {
                    'black' : 'black' in coat_list,
                    'brown' : 'brown' in coat_list,
                    'cream' : 'cream' in coat_list,
                    'gray' : 'gray' in coat_list,
                    'red' : 'red' in coat_list,
                    'white' : 'white' in coat_list,
                    'yellow' : 'yellow' in coat_list,
                }
            elif header == 'breedId':
                line[header] = str(line[header])
            else:
                line[header] = int(line[header])

        dict_list.append(line)

for i in range(0, len(dict_list), BATCH_SIZE):
    batch = store.batch()

    batched = dict_list[i:i+BATCH_SIZE]

    for single in batched:
        doc = store.collection(COLLECTION_NAME).document()
        single['id'] = doc.id
        batch.set(doc, single)

    batch.commit()
