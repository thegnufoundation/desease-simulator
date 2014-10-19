n = 100;
data = cell(n,1);
for i=1:n
   data{i} = load(sprintf('agent_sim_%d.txt',i)); 
end

figure;
for i=1:n
   d = data{i};
   plot(d(:,1),d(:,2),'color','blue');
   plot(d(:,1),d(:,3),'color','yellow');
   plot(d(:,1),d(:,4),'color','green');
   plot(d(:,1),d(:,5),'color','red');
   hold on;
end