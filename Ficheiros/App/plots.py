from get_data import create_dataframe
import matplotlib.pyplot as plt
from matplotlib import dates as mdates
from matplotlib.gridspec import GridSpec
import plotly.graph_objects as go

def get_categories(category_query):
    username = "'Tiago'"
    df = create_dataframe(username)
    categories = df['Category'].unique()

    df_categories = list()
    for category in categories:
        df_category = df.loc[df['Category'] == category].copy()

        try:
            df_category['Value'] = df_category['Value'].astype('float64')
        except ValueError:
            pass
        
        if category == category_query:
            return df_category

    #     df_categories.append(df_category)

    # return df_categories

def get_category_df(df_categories, categories, category):
    df_category = df_categories[categories.tolist().index(category)]
    return df_category

def get_type_df(df_category, type_):
    df_type = df_category.loc[df_category['Type'] == type_]
    return df_type

def time_series(category, each_type):
    df_category = get_categories(category)
    df = df_category.loc[df_category['Type'] == each_type]

    values = df['Value']
    type_of = df['Type'].iloc[0]
    dates = df['Date']

    data = [go.Bar(x = dates, 
                   y = values)]

    layout = go.Layout(title = category + ' - ' + type_of, xaxis_title = 'Date', yaxis_title = 'Values')

    return {'data':data, 'layout':layout}

def pie_chart(df):
    types = df['Type'].unique()
    values = df['Type'].value_counts()
    explode = [0.1 for i in range(len(types))]
    for value, type_ in zip(values, types):
        plt.pie(value, labels=type_, explode=explode, autopct='%1.1f%%')
    plt.title('Pie chart of ' + category)

def pie_chart_all_categories(df):
    unique_categories = df['Category'].unique()
    values = df['Category'].value_counts()
    explode = [0.1 for i in range(len(categories))]
    for value, unique in zip(values, unique_categories):
        plt.pie(value, labels=unique, explode=explode, autopct='%1.1f%%')
    plt.title('Pie chart of all categories')

def bar_chart(df):
    types = df['Type'].unique()
    values = list()
    if df['Value'].dtype == 'float64':
        for type_ in types:
            df_type = df.loc[df['Type'] == type_]
            value = df_type['Value'].sum()
            plt.bar(type_, value)

        for i, v in enumerate(values):
            plt.text(i, v-max(values)/15, str(round(v,2)), color='black', ha='center')
    else:
        for type_ in types:
            value = len(df.loc[df['Type']==type_])
            plt.bar(type_, value)

    plt.xticks(rotation=90, fontweight='light')
    plt.title('Bar chart of ' + category)
