import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib import dates as mdates

username = 'Tiago'

def create_df(username):

    db = 'tiagoal1_tracker_app'
    ext = '.csv'
    df_category = pd.read_csv(db +ext +'/'+db+'_table_Category'+ext)
    df_user = pd.read_csv(db +ext+'/'+db+'_table_User'+ext)
    df_type = pd.read_csv(db +ext+'/'+db+'_table_Type'+ext)
    df_usercategory = pd.read_csv(db +ext+'/'+db+'_table_UserCategory'+ext)
    user_idx = df_user['user_name'].loc[df_user['user_name'] == username].index[0]
    user_id = df_user['user_id'][user_idx]

    categories = list()
    categories_id = list()
    df_user = df_category.loc[df_category['user_id'] == user_id]
    for category,id in zip(df_user['category_name'],df_user['category_id']):
        categories.append(category)
        categories_id.append(id)

    categories_with_type = []
    types = list()
    i=0
    for each_id in categories_id:
        df_user = df_type.loc[df_type['category_id'] == each_id]

        types = []
        for type,id in zip(df_user['type_name'],df_user['type_id']):
            types.append({
                "Category":categories[i],
                "Type":type,
                "id":id})
        i+=1

        categories_with_type.append(types)
        
    categories_with_type_with_values = []
    types_with_values = []
    values = list()
    values_id = list()
    i=0
    category_idx = 0
    for category_idx in range(len(categories_with_type)):

        values = []
        type_idx = 0
        for type_idx in range(len(categories_with_type[category_idx])):
            category = categories_with_type[category_idx][type_idx].get('Category')

            type_id = categories_with_type[category_idx][type_idx].get('id')
            type_name = categories_with_type[category_idx][type_idx].get('Type')

            df_user = df_usercategory.loc[df_usercategory['type_id'] == type_id]

            for value, date in zip(df_user['value'],df_user['date']):
                value_final = {
                    "Category":category,
                    "Type":type_name,
                    "Value":value,
                    "Date":date
                    }
                values.append(value_final)

            categories_with_type_with_values.append(values)

    max_categories = len(categories_with_type_with_values)
    max_values = [len(x) for x in categories_with_type_with_values]

    categories = []
    types = []
    values = []
    dates = []

    for category_idx in range(len(categories_with_type_with_values)):
        for type_idx in range(len(categories_with_type_with_values[category_idx])):
            category = categories_with_type_with_values[category_idx][type_idx].get("Category")
            type = categories_with_type_with_values[category_idx][type_idx].get("Type")
            value = categories_with_type_with_values[category_idx][type_idx].get("Value")
            date = categories_with_type_with_values[category_idx][type_idx].get("Date")

            categories.append(category)
            types.append(type)
            values.append(value)
            dates.append(date)

    user = {
        "Category": categories,
        "Type": types,
        "Value": values,
        "Date": dates
    }

    df = pd.DataFrame(user)
    df.columns = ['Category','Type','Item','Date']
    df = df.drop_duplicates(subset=['Date'], keep='first')
    df['Date'] = pd.to_datetime(df['Date'])
    # drop repeteat in Item
    return df