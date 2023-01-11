import mysql.connector
def connect_to_database():
    cnx = mysql.connector.connect(
        host = "162.241.226.49",
        username = "tiagoal1_WPVHZ",
        password = "database.Almeida5",
        database = "tiagoal1_tracker_app"
    )

    return cnx.cursor(), cnx

def query_result(cursor, query):
    cursor.execute(query)
    results = cursor.fetchall()

    return results

def append_name_id(results):
    names = []
    names_ids = []
    for result in results:
        names.append(result[0])
        names_ids.append(result[1])

    return names, names_ids

def match_name2id(name, names, names_id):
    for n, idx in zip(names, names_id):
        if n == name:
            return str(idx)


cursor, cnx = connect_to_database()

user = '"Tiago"'
category_query = 'Gastos'
type_query = 'Refeições'

####################        GET USER_ID         ################################

query = "select user_id from User where user_name like " + user + ";"

results = query_result(cursor, query)

for result in results:
    idx = str(result[0])

####################        GET CATEGORIES         ################################

query = "SELECT DISTINCT category_name, category_id FROM Category where user_id like " + idx + " ORDER BY category_name;"

results = query_result(cursor, query)

categories, categories_ids = append_name_id(results)

print('\nCategories of', user + ':')
for category, ids in zip(categories, categories_ids):
    print(str(ids) + ' - ' + category)

# category_idx = input('Choose one id: ')
category_idx = match_name2id(category_query, categories, categories_ids)

####################        GET TYPES         ################################

query = "SELECT DISTINCT type_name, type_id FROM Type where category_id like " + category_idx + " ORDER BY type_name;"

# query = "select * from UserCategory where user_id like " + idx + ";"

results = query_result(cursor, query)

types, types_ids = append_name_id(results)

print('\nTypes of', category_query + ':')
for type_, ids in zip(types, types_ids):
    print(str(ids) + ' - ' + type_)

# type_idx = input('Choose one id: ')
type_idx = match_name2id(type_query, types, types_ids)

####################        GET VALUES         ################################

query = "SELECT value, date FROM UserCategory where type_id like " + type_idx + ";"

results = query_result(cursor, query)

values, dates = append_name_id(results)

print('\nValues of', type_query + ':')
for value, date in zip(values, dates):
    print(value, '-', date)

cursor.close()
cnx.close()

# https://hevodata.com/learn/mysql-export-to-csv/