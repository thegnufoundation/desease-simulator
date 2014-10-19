clear;
n = 100;
data = cell(n,1);
means = zeros(2881,5);
for i=1:n
   data{i} = load(sprintf('agent_sim_%d.txt',i)); 
end

%figure;
for i=1:n
   d = data{i};
   plot(d(:,1),d(:,2),'color','blue');
   %plot(d(:,1),d(:,3),'color','cyan');
   %plot(d(:,1),d(:,4),'color','green');
   %plot(d(:,1),d(:,5),'color','red');
   hold on;
   means = means + d;
    
end
 
 

means = means./100;
plot(d(:,1),means(:,2),'color','red','linewidth',2);
h = legend('Susceptibles (100 iterations)','Average Susceptibles');
xlabel('Days');
ylabel('Population');

leg_line=findobj(h,'type','Line')
     set(leg_line(2), 'Color', 'r');
    set(leg_line(2), 'linewidth', 2);
s = zeros(n,1);
for i=1:n
   d = data{i};
   d = d(:,2);
   f = find(d<10);
   f = f(1);
   s(i) = f;
end

m_f = mean(s)
s_s = std(s)



    
 