loop
wait
read var
rdata $var sender val
if($sender==s1)
if($val>0.23)
send $val 5 5 
end
if($val<0.19)
send $val 5 5 
end
end
if($sender==s2)
if($val>35)
send $val 6 6 
end
if($val<35)
send $val 6 6 
end
end
send $val 4 4 
