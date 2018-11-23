set mod 0
loop
if($mod==0)
areadsensor var
rdata $var t x sensorVal1
data p s1 $sensorVal1
send $p 3 
while($sensorVal1>0.19)
areadsensor var
rdata $var t x sensorVal1
data p s1 $sensorVal1
send $p 3 
delay 100000
end
if($sensorVal1<0.19)
set mod 1
end
end
if($mod==1)
while($sensorVal1<0.23)
areadsensor var
rdata $var t x sensorVal1
data p s1 $sensorVal1
send $p 3 
delay 1000
end
if($sensorVal1>0.23)
set mod 0
end
end
