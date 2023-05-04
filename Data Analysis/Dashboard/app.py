# https://dash.plotly.com/deployment
from get_data import create_dataframe
import dash
from dash import Dash, dcc, html, Input, Output
from plotly import graph_objs as go
from plotly import subplots
from datetime import date

app = Dash(name="Tracker App")
app.title = "Tracker App"

# server = app.server

colors_charts = ['#00245a','#00579E','#00378A','#0D78D0','#004ABA','#005BE2','#0066FF','#4EB2FF','#85D6FF','#3173d5','#1957B5']

colors = {
    'background': '#9AA5B1',
    'text': '#3E4C59'
}

username = "'Tiago'"
df = create_dataframe(username)

categories = df['Category'].unique()

categories_options = []
for category in categories:
    categories_options.append({'label':category,'value':category})

types = df[df['Category'] == categories[0]]['Type'].unique()

type_options = []
for type_ in types:
    type_options.append({'label':type_,'value':type_})


    
app.layout = html.Div([
    # line 1
    html.Div([
        html.Div([
            html.Div([
                dcc.Input(id='user',   
                      type='text',
                      placeholder='Username'),
            ],
            style={'display':'block'}),

            html.P(),
            
            html.Div([
                dcc.Input(id='password',
                        type='password',
                        placeholder='Password'
                ),
            ],
            style={'display':'block'}),

            html.P(),
            
            html.Div([
                html.Button('Submit', id='submit', n_clicks=0)
            ],
            style={'display':'block'})
        ],
        style={'display':'inline-block',
               'verticalAlign':'Top'},
        className='card_container'),
        # col 1
        html.Div([
            html.Div([
                html.H3('Categoria:',style={'color':colors['text']}),
                dcc.Dropdown(
                    id='dropdown-category',
                    options=categories_options,
                    value=categories[0],
                    placeholder='Category:'
                )], 
                style={'display':'block',
                    'verticalAlign': 'Top',}), 

            html.P(),

            html.Div([
                html.H3('Tipo:',style={'color':colors['text']}),

                dcc.Dropdown(
                    id='dropdown-type',
                    options=type_options,
                    value=types[0],
                    placeholder='Type:'
                )], 
                style={'display':'block',
                        'verticalAlign': 'Top',
                        'color':colors['text']}
            ),

            html.P(),
            
            html.Div([
                dcc.DatePickerRange(
                    id='date-picker-range',
                    min_date_allowed=date(2022, 11, 5),
                    max_date_allowed=date.today(),
                    initial_visible_month=date.today(),
                ),
            ], 
            style={'display':'block',
                    'verticalAlign': 'Top'}
            ),
        ], 
        style={'display':'inline-block',
                'color':colors['text']},
        className='card_container'),
    
        html.Div([
            dcc.Graph(id='categories-pie-chart',
                    figure={'data':[go.Pie(values=df['Category'].value_counts(),
                                            labels=df['Category'].value_counts().index,
                                            textposition='inside',
                                            textinfo='percent+label',
                                            marker=dict(colors=colors_charts),
                                    )],
                            'layout':go.Layout(title='Categories of {}'.format(username),
                                               height=250,
                                                showlegend=False,
                                                plot_bgcolor=colors['background'],
                                                paper_bgcolor=colors['background'],
                                                font_color=colors['text'],
                                                margin=dict(
                                                    b=0,
                                                    l=0,
                                                    r=0,
                                                    t=40)
                                                )})], 
            style={'display':'inline-block',
                   'verticalAlign': 'Top'},
            className='card_container'
        ),
    ]),


    html.Div([
        html.Div([
            dcc.Graph('pie-chart')],
            style={'width':'50%',
                'display': 'inline-block',
                'verticalAlign': 'Top'}),

        html.Div([
            dcc.Graph(id='bar-chart')],
            style={'width':'50%',
                   'display': 'inline-block',
                    'verticalAlign': 'Top'})],
        style={'height':350,
               'color':colors['text'],
               'verticalAlign': 'Top'},
        className='card_container'
    ),
    html.Div([
        dcc.Graph('bar-plot')],
        style={'color':colors['text'],
            'verticalAlign': 'Top'},
        className='card_container'
    )
])


@app.callback(Output('bar-chart','figure'),
              [Input('dropdown-category', 'value')])
def bar_chart(category):
    try: 
        df_category = df[(df['Category']==category)].copy()
        df_category['Value'] = df_category['Value'].astype('float64')

        total_sum = []
        types_sum = []
        for each in df_category['Type'].unique():
            df_type = df_category[df_category['Type'] == each]
            
            type_sum = df_type['Value'].sum()

            total_sum.append(type_sum)
            types_sum.append(each)

        data = [go.Bar(x=[tipo],
                    y=[soma],
                    orientation='v',
                    textposition='inside',
                    marker=dict(color=color),
                    text=['{:.2f}'.format(soma)]) for soma, tipo, color in zip(total_sum, types_sum, colors_charts)]

        layout = go.Layout(title='Sum of values in {}'.format(category),
                            showlegend=False,
                            height=300,
                            plot_bgcolor=colors['background'],
                            paper_bgcolor=colors['background'],
                            font_color=colors['text'],
                            margin=dict(t=30,
                                        b=20))
        

        return {'data':data,'layout':layout}
    except:
        df_category = df[df['Category'] == category]

        values = df_category['Type'].value_counts()

        data = [go.Bar(y=values,
                       x=values.index,
                       textposition='inside',
                       marker=dict(color=color),
                       text=values) for color in colors_charts]

        layout = go.Layout(title='Number of entries in {}'.format(category),
                            showlegend=False,
                            height=300,
                            plot_bgcolor=colors['background'],
                            paper_bgcolor=colors['background'],
                            font_color=colors['text'],
                            margin=dict(t=30,
                                        b=0))

        return {'data':data, 'layout':layout}


@app.callback(Output('dropdown-type', 'options'),
            [Input('dropdown-category', 'value')])
def update_type_dropdown(category):
    return  df[df['Category'] == category]['Type'].unique()

@app.callback(Output('pie-chart','figure'),
              [Input('dropdown-category', 'value')])
def pie_chart(category):
    df_category = df[df['Category'] == category]

    values = df_category['Type'].value_counts()

    data = [go.Pie(values=values,
                   labels=values.index,
                   textposition='inside',
                   textinfo='percent+label',
                   marker=dict(colors=colors_charts)
                   )]

    layout = go.Layout(title='Number of entries in {}'.format(category),
                       showlegend=False,
                       height=300,
                       plot_bgcolor=colors['background'],
                       paper_bgcolor=colors['background'],
                       font_color=colors['text'],
                       margin=dict(t=30,
                                   b=0))

    return {'data':data, 'layout':layout}


@app.callback(Output('bar-plot', 'figure'),
            [Input('dropdown-category', 'value'), Input('dropdown-type','value')])
def update_bar_plot(category,type_chose):
    df_category = df[df['Category'] == category]
    
    df_type = df_category[df_category['Type'] == type_chose]

    data = [go.Bar(x = [date],
                   y = [value],
                   marker=dict(color=color),
                   ) for date, value, color in zip(df_type['Date'], df_type['Value'], colors_charts)]

    layout = go.Layout(title = category + ' - ' + type_chose, 
                       xaxis_title = 'Date', 
                       yaxis_title = 'Values',
                       showlegend=False,
                       plot_bgcolor=colors['background'],
                       paper_bgcolor=colors['background'],
                       font_color=colors['text'],
                       margin=dict(t=30,
                                   b=20))
    
    return {'data':data, 'layout':layout}


app.run_server()