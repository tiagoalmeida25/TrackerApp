
def get_categories_unique(df):
    categories = df['Category'].unique()
    return categories

def get_categories_df(df, categories):
    df_categories = list()
    for category in categories:
        df_category = df.loc[df['Category'] == category].copy()
        df_categories.append(df_category)
        for df_category in df_categories:
            try:
                df_category['Value'] = df_category['Value'].astype('float64')
            except ValueError:
                pass 

def get_category_df(df, category):
    df_category = df.loc[df['Category'] == category].copy()
    return df_category

def get_type_df(df_category, type_):
    df_type = df_category.loc[df_category['Type'] == type_]
    return df_type