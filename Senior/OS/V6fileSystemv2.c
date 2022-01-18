// C program for a v6 file system
// Omkar Jagdale (osj170000): openfs, Count-free, initfs code and debugging
// Nigel Lacewell: openfs and initfs code and debugging
// Rishi Chandra (rxc170008): Debugging, formatting/commenting
#include <sys/types.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <math.h>

// Struct for the superblock
typedef struct {
  int isize;
  int fsize;
  int nfree;
  unsigned int free[250];
  unsigned int ninode;
  unsigned int inode[250];
  int flock;
  int ilock;
  unsigned int fmod;
  unsigned int time;
} superblock_type; // Blocksize is 2048 Bytes

// Init global superblock
superblock_type superBlock;

// Struct for a free block
typedef struct{
  int nfree;
  unsigned int free[250];
} FreeBlock;

// i-Node Structure
typedef struct {
  unsigned short flags;
  unsigned int nlinks;
  unsigned int uid;
  unsigned int gid;
  unsigned int size;
  unsigned int addr[9];
  unsigned short actime[2];
  unsigned short modtime[2];
  unsigned short dummy; //not used
} inode_type; //64 Bytes in size


typedef struct {
  unsigned int inode;
  unsigned char filename[28];
} dir_type;//32 Bytes long


// Global filesystem vars
int fileDescriptor;
int block_size = 2048;
int inode_size = 64;
int openStatus = 0;

// Function to open a file
int openfs(char * filePath) {
    
  if(access(filePath, 0) != -1) {
    
    fileDescriptor = open(filePath, 2);
    
    if(fileDescriptor != -1)
      { 
        // If the file was opened, navigate to the first block
        lseek(fileDescriptor, block_size, 0);
        read(fileDescriptor, &superBlock, block_size);
        printf("File Opened\n");
        
        return 0;

      } else {
      printf("File could not be opened\n");
      return 1;
    }

  } else { // If file dosn't exist, create one
    printf("File does not exist\n");
    printf("Creating one\n");
    fileDescriptor = creat(filePath, S_IWUSR | S_IRUSR);
    printf("File Created\n");
    return -1;
  }
}

// Function to report the number of free data blocks and number of free i-nodes available in the system.
int CountFree() {
    
  int countFreeBlocks = 0;
  int countFreeInodes = 0;
  lseek(fileDescriptor, block_size, 0);
  read(fileDescriptor, &superBlock, sizeof(superBlock));
    
  // Go to the first free block
  countFreeBlocks = superBlock.nfree;
  if(superBlock.free[0] == 0)
    {
      // do nothing, there are no free blocks
    }
  else
    {
      int nextFree = superBlock.free[0];
      // Loop till there are no free blocks left
      while(nextFree != 0){
	FreeBlock f;
	// Seek to the blocks
	lseek(fileDescriptor, nextFree * block_size, 0);
	read(fileDescriptor, &f, sizeof(f));
	// Increment free count and go to next free block
	countFreeBlocks += f.nfree;
	nextFree = f.free[0];
      }
    }
  // Done counting free blocks
  int i;
  inode_type inode1;
  // Iterate through i nodes and increment count
  for(i = 0; i < (superBlock.isize * 32); i++){
    lseek(fileDescriptor, (block_size*2) + (i* inode_size), 0);
    read(fileDescriptor, &inode1, inode_size);
    if(inode1.flags == 0){
      countFreeInodes++;
    }
  }
  printf("The number of free blocks: %d and the number of free inodes: %d\n", countFreeBlocks, countFreeInodes);
}

// Helper Function to 
void MakeCopy(int index)
{
  FreeBlock S;
  S.nfree = 250;
  int i;
  for(i = 0; i < 250; i++)
    {
      S.free[i] = superBlock.free[i];
    }
  lseek(fileDescriptor, index*block_size, 0);
  write(fileDescriptor, &S, sizeof(S));
}


void initializeBlocks(int index)
{
  lseek(fileDescriptor, block_size, 0);
  read(fileDescriptor, &superBlock, sizeof(superBlock));
  
  if(superBlock.nfree == 250)
    {
      MakeCopy(index);
      superBlock.nfree = 1;
      superBlock.free[0] = index;
    }
  else
    {
      superBlock.free[superBlock.nfree++] = index;
    }

  lseek(fileDescriptor,block_size,0);
  write(fileDescriptor,&superBlock,sizeof(superBlock));
}

addEmptyBlock(FreeBlock f, int block_num){
  lseek(fileDescriptor, block_size*block_num, 0);
  write(fileDescriptor, &f, sizeof(f));
}

addEmptyInode(inode_type inode1, int inode_num){
  lseek(fileDescriptor, (block_size*2) + (inode_num * inode_size), 0);
  write(fileDescriptor, &inode1, inode_size);
}

// Parameters: n1 is the file system size in number of blocks and n2 is the number of blocks devoted to the i-nodes. 
// Function to set all data blocks free according to the instructions
int InitFS(int n1, int n2) {
  // initialize the members
  superBlock.isize = n2;
  superBlock.fsize = n1;
    
  superBlock.nfree = 0;
  long i;
  for (i = 0; i < 250; i++) {
        
    superBlock.free[i] = 0;
  }
    
  superBlock.ninode = 250;
  for (i = 0; i < 250; i++) {
        
    superBlock.inode[i] = 0;
  }
       
  superBlock.time = (unsigned int) time(0);
    
  lseek(fileDescriptor, block_size, 0);
  write(fileDescriptor, &superBlock, sizeof(superBlock));
    
  int first_block = 2 + superBlock.isize + 1;
  int next_block;

  // Add Free blocks and Free Inodes
  FreeBlock f;
  f.nfree = 0;
  f.free[0] = 0;
  for (next_block = first_block; next_block < n1; next_block++) {
    addEmptyBlock(f, next_block);
  }
  inode_type inode1;
  inode1.flags = 0;
  // Since 32 inodes per block
  for(i = 0; i < (superBlock.isize * 32); i++)                  {
    addEmptyInode(inode1, i);
  }
  

  // Fill the free array and form a chain when required
  for (next_block = first_block; next_block < n1; next_block++) {
    initializeBlocks(next_block);
  }
    
  superBlock.ninode = 249;
  int next_inode = 1;
  for (i = 0; i < 249; i++) {
        
    superBlock.inode[i] = next_inode;
    next_inode++;
  }
    
  lseek(fileDescriptor, block_size, 0);
  write(fileDescriptor, &superBlock, block_size);
    
  dir_type root_dir;
    
  inode_type rootInode;
  rootInode.flags = 0;
  rootInode.flags |= 1 << 15;
  rootInode.flags |= 1 << 14;
  rootInode.flags |= 0 << 13;
  rootInode.nlinks = 0;
  rootInode.uid = 0;
  rootInode.gid = 0;
  rootInode.size = 0;
  int j;
  for (j = 1; j < 9; j++) {
        
    rootInode.addr[j] = 0;
  }
    
  for (j = 0; j < 2; j++) {
        
    rootInode.actime[j] = 0;
  }
    
  for (j = 0; j < 2; j++) {
        
    rootInode.modtime[j] = 0;
  }
    
  root_dir.inode = 1;
  strcpy(root_dir.filename, ".");
    
  lseek(fileDescriptor, (first_block - 1) * block_size, 0);
  write(fileDescriptor, &root_dir, 32);
  strcpy(root_dir.filename, "..");
  write(fileDescriptor, &root_dir, 32);
    
  rootInode.addr[0] = first_block - 1;
  
  lseek(fileDescriptor, (block_size * 2), 0);
  write(fileDescriptor, &rootInode, inode_size);
    
  return 0;
}

// Main Function to Act as the prompts for the v6 file system
int main()
{
  // Prompt the user 
  printf("\n## Please enter a command ##\n");
  printf("\n\t - openfs = Open File System \n");
  printf("\n\t - initfs = Initialize File System \n");
  printf("\n\t - count-free = Count number of free blocks and free inodes\n");
  printf("\n\t - q = Quit out\n");

  // Init local vars
  short status;
  char cmdInput[256];
  char * filePath;
  char * fp;
  
  // Loop menu options
  while (1) {

    printf("Enter Command: ");
    scanf(" %[^\n]s", cmdInput);
    
    if(strcmp(cmdInput, "q") == 0) {
      exit(0);
    }
    char * cmd;
    cmd = strtok(cmdInput, " ");
    //char * filePath;
    printf("%s\n", filePath);
    // Open FS command
    if (strcmp(cmd, "openfs") == 0) {
      fp = strtok(NULL, " ");
      int x = openfs(fp);
      openStatus = 1;
      strcpy(filePath, fp);
      
    } 
    // initfs command
    else if (strcmp(cmd, "initfs") == 0) {
      char * nBlocks = strtok(NULL, " ");
      char * nInodes = strtok(NULL, " ");
      long nblk = atoi(nBlocks);
      long ninds = atoi(nInodes);
      //printf("%s\n", filePath);
      if(openStatus != 1){
        printf("\nERROR: File not opened yet \n");
        continue;
      }
      if (fileDescriptor == -1) {
	printf("\nERROR: Invalid File Descriptors \n");
	exit(0);
      } 
      else {
	printf("\n Initalizing new File System... \n", filePath);
	status = InitFS(nblk, ninds);
	if (status != 0) {
	  printf("\nERROR: Initialization failed\n");
	}
	else {
	  printf("\nInitialization done\n");
	}
      }
      // }
    } 
    // Count Free Command
    else if (strcmp(cmd, "count-free") == 0) {
      //in this case, report the number of free data blocks and number of free i-nodes available in the system.
      if(openStatus != 1){
        printf("\nERROR: File not opened yet \n");
        continue;
      }
      CountFree();
    }
  }
}
