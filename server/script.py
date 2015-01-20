hello = 'hello!'
a = 2
b = 5
c = 3
k = 0.1
# ==========================================
count = 0
while count < 5:
   print count, " is  less than 5"
   count = count + 1
else:
   print count, " is not less than 5"

# ==========================================
fruits = ['banana', 'apple',  'mango']
for index in range(len(fruits)):
   print 'Current fruit :', fruits[index]
   
# ==========================================
for num in range(10,20):  
   for i in range(2,num):
      if num%i == 0:      
         j=num/i          
         print '%d equals %d * %d' % (num,i,j)
         break 
      print num, 'is a prime number'
    
# ==========================================    
def calc(c, d):
    result = c + d
    result = c * d
    result = c - d
    result = (c / d) - d + (c*d)
    return result

# ==========================================
print hello, calc(a, b)

# ==========================================
class MyClass:
    i = 12345
    i = 0.2
    def f(self):
        return 'hello world'
    
class MyClassss:
    i = 12345
    i = 0.2
    def f(self):
        return 'hello world'