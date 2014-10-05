import sys
import matplotlib.pyplot as plt

# Get the data from stdin.
data = sys.stdin.readlines()

# Compute the population.
population = data[0].split()
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
    S.append(int(m[0])/population)
    E.append(int(m[1])/population)
    I.append(int(m[2])/population)
    R.append(int(m[3])/population)

# Create the plot.
plt.plot(t,S,linewidth=2,label="Suspectible")
plt.plot(t,E,linewidth=2,label="Exposured")
plt.plot(t,I,linewidth=2,label="Infected")
plt.plot(t,R,linewidth=2,label="Recovered")
plt.ylabel('population')
plt.ylabel('days')
plt.legend()
plt.show()