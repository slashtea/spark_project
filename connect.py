import MySQLdb
import plotly.plotly as py
import plotly.graph_objs as go
import matplotlib.pyplot as plt
import sys

x = []
y = []
my_xtricks = []
i=1

# Database connection
connection = MySQLdb.connect(host = "127.0.0.1", user = "root", passwd = "Australia4ever", db = "tweetData")
cursor = connection.cursor()
cursor.execute ("SELECT country, count( country ) AS cs FROM tweets WHERE sentiment = 'positive' GROUP BY country")
data = cursor.fetchall()

for row in data :
	my_xtricks.append(row[0])
	x.append(i)
	y.append(row[1])
	i = i + 1

cursor.close()
# print x
plt.xticks(x, my_xtricks)
plt.bar(x, y, width=0.1)
plt.xlabel('Country')
plt.ylabel('Number of tweets')
plt.legend('Number of positive tweets by country')
plt.show()

# trace1 = go.Scatter(
#      x=my_xtricks,
#      y=y,
#      mode='markers'
# )
# layout = go.Layout(
#      xaxis=go.XAxis( title='Life Expectancy' ),
#      yaxis=go.YAxis( type='log', title='GNP' )
# )
# data = go.Data([trace1])
# fig = go.Figure(data=data, layout=layout)
# py.iplot(fig, filename='world GNP vs life expectancy')