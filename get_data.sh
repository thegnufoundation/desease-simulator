for i in {51..100}
do
	fname='agent_sim_'$i'.txt'
   	echo 'Will be saving to:./data/'$fname'\n'
	java  -classpath ./build/classes dsmv_simulation.Simulation 120 10000 1 > ./data/$fname
done
