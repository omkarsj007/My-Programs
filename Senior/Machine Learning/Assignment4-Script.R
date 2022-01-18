#install.packages('mlbench')
#install.packages('rpart')
#install.packages('rpart.plot')
#install.packages('caret')
#install.packages('lattice')
#install.packages('e1071')
#install.packages('doParallel')
#install.packages('foreach')
#install.packages('iterators')
#install.packages('parallel')

# Omkar Jagdale osj170000 CS4375.0W2

library(mlbench)
library(rpart)
library(rpart.plot)
library(ggplot2)
library(lattice)
library(e1071)
library(caret)
library(parallel)
library(iterators)
library(foreach)
library(doParallel)
library(dplyr)

###################################################################
#4
Person_id <- c(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
Home_owner <- c("Yes", "No", "Yes", "No", "Yes", "No", "No", "No", "No", "Yes")
Refund <- c("true", "true", "false", "false", "false", "true", "true", "false", "false", "false")

myData <- data.frame(Person_id, Home_owner, Refund)

for(i in c(2:3))
  myData[[i]] <- as.factor(myData[[i]])


sampleIds <- sample(myData$Person_id, replace = TRUE, 10)
bootStrapSample <- myData[sampleIds,]
bootStrapSample
# Ran lines 39-41 ten times to get the 10 samples
###################################################################


mydata <- read.csv("C:\\Users\\omkar\\Downloads\\bank-additional.csv", sep=";", header = TRUE)
summary(mydata)

mydataNA <- na.omit(mydata)
improvedMyData <- unique(mydataNA[complete.cases(mydataNA),])

for(i in c(2:10, 15))
  improvedMyData[[i]] <- as.factor(improvedMyData[[i]])

summary(improvedMyData)



accuracy <- function(truth, prediction) {
  tbl <- table(truth, prediction)
  sum(diag(tbl))/sum(tbl)
}


#------------------------------------------------------------------------------


index <- 1:nrow(improvedMyData)
index <- sample(index)

fold <- rep(1:10, each=nrow(improvedMyData)/10)[1:nrow(improvedMyData)]

folds <- split(index, fold) ### create list with indices for each fold

summary(index)

testingError <- vector(mode="numeric")
trainingError <- vector(mode="numeric")
# Create a tree for each fold and run it on the first fold
# i.e. the testing set
############################################
# 6
for(i in 1:length(folds)) {
  tree <- rpart(y ~., data=improvedMyData[-folds[[i]],], control=rpart.control(minsplit=2, cp=0.128497202))
  accs <- accuracy(improvedMyData[folds[[i]],]$y, predict(tree, improvedMyData[folds[[i]],], type="class"))
  testingError[i] <- 1 - accs
  trainingError[i] <- 1 - accuracy(improvedMyData[-folds[[i]],]$y, predict(tree, improvedMyData[-folds[[i]],], type="class"))
    }
testingError
trainingError
mean(accs)
mean(testingError)
# cp=0.003197442
# Average accuracy = 0.9148418
# Average testing error = 0.09902676

# cp=0.006705747
# Average accuracy = 0.9051095
# Average testing error = 0.0946472

# cp=0.036903810
# Average accuracy = 0.9148418
# Average testing error = 0.09440389

# cp=0.064481748
# Average accuracy = 0.8734793
# Average testing error = 0.1060827

# cp=0.128497202
# Average accuracy = 0.8734793
# Average testing error = 1097324

# cp = 0.003197442 and cp = 0.036903810 have the highest accuracy
# of 0.9148418. However, cp = 0.036903810 has a lower testing error
# of 0.09440389 as compared to cp = 0.003197442's 0.009902676.

# cp = 0.036903810 is the choice
######################################################################
#7

fit <- train(y ~ ., data = improvedMyData , method = "rpart",
             control=rpart.control(minsplit=2),
             trControl = trainControl(method = "cv", number = 10),
             tuneLength=5)

fit
fit$finalModel
# The three most important attributes are
# duration, nr.employed, and pdays

rpart.plot(fit$finalModel, extra = 2, under = TRUE, varlen=0, faclen = 0)

varImp(fit, compete = FALSE)

dotPlot(varImp(fit, compete = FALSE))
#######################################################################
#8
# The 5 attributes used are:
# duration, nr.employed, pdays, month, emp.var.rate
subsetMyData <- improvedMyData[,c(9, 11, 13, 16, 20, 21)]
subsetMyData[[1]] <- as.numeric(subsetMyData[[1]])
myData_scaled <- cbind(as.data.frame(scale(subsetMyData[,-6])), type = subsetMyData[,6])

#######################################################################
#9
trainMyData <- createFolds(myData_scaled$type, k=10)
trainMyData

knnFit <- train(type ~ ., method = "knn", data = myData_scaled,
                tuneLength = 5, tuneGrid = data.frame(k=1:10),
                trControl = trainControl(
                  method = "cv", indexOut = trainMyData))
knnFit
# the final value used for k is 1 as it has the highest accuracy:
# 0.9837331
                
