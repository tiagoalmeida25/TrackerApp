import mysql.connector
import pandas as pd

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
        names_ids.append(str(result[1]))

    return names, names_ids

def match_name2id(name, names, names_id):
    for n, idx in zip(names, names_id):
        if n == name:
            return str(idx)

def get_user_id(cursor, user):
    query = "select user_id from User where user_name like " + user + ";"

    results = query_result(cursor, query)

    for result in results:
        idx = str(result[0])

    return idx

def get_types(cursor, category_idx):
    query = "SELECT DISTINCT type_name, type_id FROM Type where category_id like " + category_idx + " ORDER BY type_name;"
    results = query_result(cursor, query)
    types, types_ids = append_name_id(results)

    return types, types_ids

def get_values(cursor, type_idx):
    query = "SELECT value, date FROM UserCategory where type_id like " + type_idx + ";"
    results = query_result(cursor, query)
    values, dates = append_name_id(results)

    return values, dates

def create_dataframe(user):
    cursor, cnx = connect_to_database()

    idx = get_user_id(cursor, user)

    query = "SELECT DISTINCT category_name, category_id FROM Category where user_id like " + idx + " ORDER BY category_name;"
    results = query_result(cursor, query)
    categories, categories_ids = append_name_id(results)

    categories_df = []
    types_df = []
    values_df = []
    dates_df = []
    for category, category_idx in zip(categories, categories_ids):
        types, types_ids = get_types(cursor, category_idx)

        for type_, type_idx in zip(types, types_ids):
            # print('Category:', category)
            # print('Type:', type_)
            values, dates = get_values(cursor, type_idx)

            for value, date in zip(values, dates):
                categories_df.append(category)
                types_df.append(type_)
                values_df.append(value)
                dates_df.append(date)

    user = {
            "Category": categories_df,
            "Type": types_df,
            "Item": values_df,
            "Date": dates_df
        }

    df = pd.DataFrame(user)
    df.columns = ['Category','Type','Value','Date']
    df = df.drop_duplicates(subset=['Date'], keep='first')
    df['Date'] = pd.to_datetime(df['Date'])

    cursor.close()
    cnx.close()

    return df