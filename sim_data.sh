fname='agent_sim_'$1'_'$3'.txt'
echo 'Will be saving to :'$fname'\n'
java  -classpath ./build/classes dsmv_simulation.Simulation $1 $2 $3 > $fname
