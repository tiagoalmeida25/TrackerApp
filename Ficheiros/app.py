# https://dash.plotly.com/deployment
from get_data import create_dataframe
import plots
import dash
from dash import Dash, dcc, html, Input, Output
# import dash_bootstrap_components as dbc
from dash import dcc
from dash import html

app = Dash(name="Tracker App")
app.title = "Tracker App"

# server = app.server

username = "'Tiago'"
df = create_dataframe(username)
categories = df['Category'].unique()

app.layout = html.Div([
    html.H2('Choose a category:'),
    dcc.Dropdown(categories,
        categories[0],
        id='dropdown'
    ),
    html.Div(id='display-categories')
])

# @app.callback(Output('display-categories', 'children'),
#                 [Input('dropdown', 'value')])
# def display_value(value):
#     return f'You have selected {value}'


@app.callback(Output(component_id='bar_plot', component_property= 'figure'),
              [Input(component_id='dropdown', component_property= 'value')])
def graph_update(dropdown_value):
    fig = plots.time_series(dropdown_value, 'Refeições')
    return fig  

# app.layout = html.Div(id = 'parent', children = [
#         html.H1(id = 'H1', children = 'Styling using html components', 
#         style = {'textAlign':'center', 
#                  'marginTop':40,
#                  'marginBottom':40}),
#         dcc.Graph(id = 'bar_plot', figure = plots.time_series(value, 'Refeições'))
#     ])



app.run_server()