"""
Az adott szkript a Google Forms-ról letöltött kérdőíveket szűri és alakítja át kedvező formátumba

A szkript létrehoz egy új csv fájlt a CSV_OUTPUT_FILE-ban megadott névvel, amely
a CSV_INPU_FILE-ban megadott nevű Google Forms-ról letöltött csv fájlt alakítja át.
Az átalakítás során a szkript átalakítja a fejléceket a NEW_FIELD_NAMES listában
tárolt sztringekre, valamint a kutyafajtákat tartalmazó oszlop értékeit
lecseréli a BREED_MAPPING dictionary-ben meghatározott egész számokra. Az olyan kutyafajták, amelyek nincsenek a listában törlésre kerülnek.
"""

import csv
from datetime import datetime

BREED_MAPPING = {
    "Tacskó": 0, "Francia bulldog": 1, "Labrador retriever": 2, "Golden retriever": 3,
    "Német juhászkutya": 4, "Uszkár": 5, "Cane corso": 6, "Border collie": 7, "Szibériai husky": 8,
    "Shiba Inu": 9, "Csivava": 10, "Mopsz": 11, "Bichon": 12, "Rottweiler": 13, "Akita": 14,
    "Beagle": 15, "Puli": 16, "Berni pásztorkutya": 17, "Mudi": 18, "Máltai selyemkutya": 19,
    "Jack Russell terrier": 20, "Bullterrier": 21, "Vizsla": 22, "Komondor": 23, "Kuvasz": 24,
    "Yorkshire terrier": 25, "Ausztrál juhászkutya": 26, "Bernáthegyi": 27, "Boston terrier": 28,
    "Shar pei": 29, "Dobermann": 30, "Pumi": 31, "Alaszkai malamut": 32, "Pekingi palotakutya": 33,
    "Chow-Chow": 34, "Szamojéd": 35, "Basset hound": 36, "Dalmata": 37,
    "Amerikai staffordshire terrier": 38, "Dán dog": 39, "Cocker spániel": 40, "Bullmasztiff": 41,
    "Schnauzer": 42, "Malinois": 43, "foxterrier": 44, "Staffordshire bullterrier": 45,
    "Újfundlandi": 46, "Ír szetter": 47, "Cavalier King Charles spániel": 48, "Tibeti terrier": 49
}

NEW_FIELD_NAMES = [
    'time', 'breedId', 'age', 'colors', 'size', 'feeding', 'hairLength', 'hairTexture',
    'grooming', 'shedding', 'walking', 'playfulness', 'intelligence', 'kidTolerance',
    'catTolerance', 'healthProblems', 'loudness', 'independence', 'defensiveness'
]

CSV_INPU_FILE = "google-forms-data.csv"
CSV_OUTPUT_FILE = "filtered-data.csv"


def convert_csv(input_file, output_file, breed_dict, new_field_names):
    with open(input_file, 'r', newline='', encoding="utf-8") as in_csv, \
        open(output_file, 'w', newline='', encoding="utf-8") as out_csv:
        reader = csv.DictReader(in_csv, fieldnames=new_field_names)
        writer = csv.DictWriter(out_csv, fieldnames=new_field_names)
        writer.writeheader()

        for row in reader:
            breed_value = row.get('breedId')
            if breed_value in breed_dict:
                time_value = row.get('time')
                row['time'] = int(datetime
                    .strptime(
                        time_value.replace('de. ', '').replace('du. ', ''),
                        '%Y/%m/%d %I:%M:%S EET'
                    )
                    .timestamp() * 1000)

                row['breedId'] = str(breed_dict[breed_value])

                age_value = row.get('age') \
                    .replace('Kölyök', '0') \
                    .replace('Középkorú', '1') \
                    .replace('Idős', '2')
                row['age'] = age_value

                colors_value = row.get('colors') \
                    .replace('sárgásbarna', 'yellow') \
                    .replace('fehér', 'white') \
                    .replace('vörös', 'red') \
                    .replace('szürke', 'gray') \
                    .replace('krémszínű', 'cream') \
                    .replace('barna', 'brown') \
                    .replace('fekete', 'black')
                row['colors'] = colors_value

                writer.writerow(row)


convert_csv(CSV_INPU_FILE, CSV_OUTPUT_FILE, BREED_MAPPING, NEW_FIELD_NAMES)