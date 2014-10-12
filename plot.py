import sys
import matplotlib.pyplot as plt

# Get the data from stdin.
data = sys.stdin.readlines()

# Compute the population.
population = data[0].split()

if(int(population[0])==-1):
	exit()

population = int(population[0]) + int(population[1]) + int(population[2]) + int(population[3])
population = population*1.0

# Compute the number of simulation days.
t = range(0,len(data))
for i in range(0,len(t)):
    t[i] = t[i]/24.0

# Append the normalized data to array lists.
S = []
E = []
I = []
R = []

for i in range(0,len(data)):
    m = data[i].split()
    S.append(int(m[0]))
    E.append(int(m[1]))
    I.append(int(m[2]))
    R.append(int(m[3]))

# Create the plot.
axes = plt.gca()
axes.set_ylim([0,population+population*0.4])
plt.plot(t,S,linewidth=2,label="Suspectible")
plt.plot(t,E,linewidth=2,label="Exposured")
plt.plot(t,I,linewidth=2,label="Infected")
plt.plot(t,R,linewidth=2,label="Recovered")
plt.ylabel('population')
plt.xlabel('days')
plt.legend()
plt.show()
