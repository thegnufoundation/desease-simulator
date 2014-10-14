# Import some libraries.
import scipy, scipy.integrate
import pylab

class SEIR_Model():
    def __init__(self,beta,gamma,mu,a,Y0):
        self.beta = beta
        self.gamma = gamma
        self.mu = mu
        self.a = a	
        self.Y0 = Y0
        self.Y = None
    
    def __computeRDiff(self,y,t):
        S = y[0]
        E = y[1]
        I = y[2]
        R = y[3]
        N = S + E + I + R
        dS = self.mu * N - self.mu * S - self.beta * I * S / N
        dE = self.beta * I * S / N - (self.mu + self.a) * E
        dI = self.a * E - (self.gamma + self.mu) * I
        dR = self.gamma * I - self.mu * R
        return [dS, dE, dI, dR]
    
    def __solveDiff(self,time_interval):
        return scipy.integrate.odeint(self.__computeRDiff, self.Y0, time_interval)
    
    def simulate(self,time_interval):
        # Create a time interval.
        t = scipy.linspace(0, time_interval, 1000)
        
        # Compute the values for that time interval by solving the
        # differential equations.
        Y = self.__solveDiff(t)
        S = Y[:, 0]
        E = Y[:, 1]
        I = Y[:, 2]
        R = Y[:, 3]
        data_file = open('seir.dat', 'w')

        for point in Y:
            data_file.write('%f %f %f %f\n'%(point[0],point[1],point[2],point[3]))
            
        pylab.figure()
        pylab.plot(t, S, t, E, t, I, t, R)
        pylab.xlabel('Time')
        pylab.ylabel('Population')
        pylab.legend([ 'Susceptible', 'Exposed', 'Infectious', 'Recovered' ])
        pylab.show()

if __name__ == "__main__":
    population = 10000 # The population of Katsina
    beta = 0.9
    gamma = 0.1
    mu = 0 # Attempt...
    a = 0.07
    S0 = population - 1
    E0 = 0
    I0 = 1
    R0 = 0
    Y0 = [ S0, E0, I0, R0 ]
    s = SEIR_Model(beta,gamma,mu,a,Y0)
    s.simulate(100)
