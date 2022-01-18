// C++ program to multiply two numbers represented 
// as strings. 
// Omkar Jagdale (osj170000): Coded the thread function, modified multiply function
// Nigel Lacewell:worked on vector translation, output to file, and debugging 
// Rishi Chandra (rxc170008): Worked on egde cases, debugging, and formatting 
#include<bits/stdc++.h> 
#include<string>
#include<iostream>
#include<sstream>
#include<pthread.h>
#include<semaphore.h>
#include<unistd.h>
#include<fstream>
#include<vector>
using namespace std; 

vector <int> num_1(256, 0);
vector <int> num_2(256, 0);
int num_threads = 0;
string str1, str2;
const int MAX = 100;     // Maximum number of threads allowed
sem_t semaphore;      // global semaphore, used for mutual exclusion
pthread_t tid[ MAX ];   // array of thread identifiers

     

// will keep the result number in vector in reverse order 
vector<int> result(512, 0); 
// Multiplies num_1 and num_2, and prints result. 
vector <int> equalDivide(256, 0);
// to divide digits among threads as much as possible

void multiply(int len1, int len2, int lowerBound, int upperBound) 
{ 
  
   if (len1 == 0 || len2 == 0) 
    return ; 
  // Below two indexes are used to find positions in result. 
  int i_n1 = len1 - upperBound - 1; 
  int i_n2 = 0; 
  // Go from right to left in num1 
  for (int i=upperBound; i >=lowerBound; i--) 
    { 
      int carry = 0; 
      int n1 = num_1[i]; 
      
      // To shift position to left after every multiplication of a digit in num2 
      i_n2 = 0; 

      // Go from right to left in num2 
      for (int j=len2-1; j>=0; j--) 
      { 
        // Take current digit of second number 
        int n2 = num_2[j]; 

 
        // Store result
        
        // Multiply with current digit of first number 
        // and add result to previously stored result at current position. 
        int sum = n1*n2 + result[i_n1 + i_n2] + carry; 

        // Carry for next iteration 
        carry = sum/10; 
        result[i_n1 + i_n2]  = sum % 10; 

        i_n2++; 
      } 

      // store carry in next cell 
      if (carry > 0){
	result[i_n1 + i_n2] += carry; 
      }
      // To shift position to left after every 
      // multiplication of a digit in num1. 
      i_n1++; 
    }
  
 }

// Divide the digits among the threads on the basis of threadNum
void * thread_func(void*arg){
  long threadNum = (long)arg;
  int upperBound = 0;
  int lowerBound = 0;

  if(threadNum == 0)
    {
      lowerBound = 0;
      upperBound = equalDivide[threadNum];
    }
  else
    {
      lowerBound = equalDivide[threadNum - 1] + 1;
      upperBound = equalDivide[threadNum];
    }


  sem_wait(&semaphore);
  multiply(str1.length(), str2.length(), lowerBound, upperBound);
  sem_post(&semaphore);
  return NULL;
}

 
// Driver code 
int main() 
{ 
  ifstream infile;
  infile.open("file1.txt");
  if(!infile.is_open())
    cout << "NOPE" << endl;
  infile >> num_threads;
  infile >> str1;
  infile >> str2;
  infile.close();
  
  if (str1.length() < num_threads){
    num_threads = str1.length();
  }
  if (num_threads > MAX){
    num_threads = MAX;
  }
  int num1;
  int num2;
  
  int x;
  // fill the numbers into the respective vectors
  for(x = 0; x < str1.length(); x++)
    {
      num_1[x] = str1[x] - '0';
    }
  for(x = 0; x < str2.length(); x++)
    {
      num_2[x] =  str2[x] - '0';
    }

  cout << endl;

  // equalDivide will have the number of digits for each thread in each index position
  // Digits evenly divided among threads
  // Ex: 8 digits 5 threads would have equalDivide = [2, 2, 2, 1, 1]
  for(x = 0; x < str1.length(); x++)
    {
      equalDivide[x % num_threads]++;
    }

  // equalDivide  will have the ending indices for each thread in each index position
  // Ex: the vector would be modified to look like [1, 3, 5, 6, 7]
  int sum = -1;
  for(x = 0; x < num_threads; x++)
    {
      equalDivide[x] += sum;
      sum = equalDivide[x];
    }
  // each thread will access an index position in accordance to their thread number



  // semaphore initialized
  sem_init(&semaphore, 0, 1);


  // Threads created
  int i;
  for( i = num_threads-1; i >= 0; i-- ) 
    {    pthread_create( &tid[ i ], NULL, thread_func, (void *) i );
  
    pthread_join( tid[ i ], NULL );
    
}

    // ignore '0's from the right 
  i = result.size() - 1; 
  while (i>=0 && result[i] == 0) 
    i--; 

  string s = "";
  // If all were '0's - means either both or 
  // one of num1 or num2 were '0' 
  if (i == -1) 
    s = "0"; 

  // generate the result string 
   
  stringstream ss;
  while (i >= 0){ 
    ss << result[i--];
  }
  s = ss.str();
  
  ofstream myfile("output.txt");
  myfile << s;
  myfile.close();
 
 return 0; 
} 
