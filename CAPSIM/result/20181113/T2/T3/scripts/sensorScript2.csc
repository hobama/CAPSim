loop
areadsensor var
rdata $var t x count
data sendVar s2 $count
send $sendVar 3 3 
delay 5000
