#install.packages('randomForest')
#install.packages('gbm')
#install.packages('ISLR')
#install.packages("writexl")

# Omkar Jagdale osj170000 CS4375.0W2


library(rpart)
library(rpart.plot)
library(randomForest)
library(gbm)
library(lattice)
library(ggplot2)
library(caret)
library(ISLR)
library(writexl)

rootMeanSquare = function(actual, predicted){
  sqrt(mean((actual - predicted) ^ 2))
}
#1

data("Carseats")
head(Carseats)

mydataNA <- na.omit(Carseats)
improvedMyData <- unique(mydataNA[complete.cases(mydataNA),])

for(i in c(7, 10, 11))
  improvedMyData[[i]] <- as.factor(improvedMyData[[i]])


set.seed(18)
randomIds <- sample(1:nrow(improvedMyData), nrow(improvedMyData)*0.66)
trainingSet <- improvedMyData[randomIds,]
testingSet <- improvedMyData[-randomIds,]

regressionTree = rpart(Sales ~ ., data = trainingSet)
rpart.plot(regressionTree, under = TRUE, varlen=0, faclen=0)

regressionTreePred = predict(regressionTree, newdata = testingSet)

plot(regressionTreePred, testingSet$Sales,
     xlab = "Predicted", ylab = "Actual", 
     main = "Predicted vs Actual: Single Tree, Test Data",
     col = "dodgerblue", pch = 20)
grid()
abline(0, 1, col = "darkorange", lwd = 2)

(regressionTreeRMSE = rootMeanSquare(regressionTreePred, testingSet$Sales))
# The RMSE is 2.238376
####################################################################################
#2
# creating Linear Regression  Model
regressionLM = lm(Sales ~ ., data = trainingSet)
regressionLMPred = predict(regressionLM, newdata = testingSet)
plot(regressionLMPred, testingSet$Sales,
     xlab = "Predicted", ylab = "Actual",
     main = "Predicted vs Actual: Linear Model, Test Data",
     col = "dodgerblue", pch = 20)
grid()
abline(0, 1, col = "darkorange", lwd = 2)

(regressionLM_RMSE = rootMeanSquare(regressionLMPred, testingSet$Sales))
# The RMSE is 1.086131
#################################################################################
#3
myDataBag = randomForest(Sales ~ ., data = trainingSet, mtry = 10,
                         importance = TRUE, ntrees = 500)

myDataBagPred = predict(myDataBag, newdata = testingSet)
plot(myDataBagPred, testingSet$Sales,
     xlab = "Predicted", ylab = "Actual",
     main = "Predicted vs Actual: Bagged Model, Test Data",
     col = "dodgerblue", pch = 20)
grid()
abline(0, 1, col = "darkorange", lwd = 2)

(myDataBag_RMSE = rootMeanSquare(myDataBagPred, testingSet$Sales))
# The RMSE is 1.758719
####################################################################################
#4

myForest <- randomForest(Sales ~., data = trainingSet, mtry = 3,
                         importance = TRUE, ntrees = 500)

myForestPred = predict(myForest, newdata = testingSet)
plot(myForestPred, testingSet$Sales,
     xlab = "Predicted", ylab = "Actual",
     main = "Predicted vs Actual: Random Forest, Test Data",
     col = "dodgerblue", pch = 20)
grid()
abline(0, 1, col = "darkorange", lwd = 2)
                       
(myForest_RMSE = rootMeanSquare(myForestPred, testingSet$Sales))
# The RMSE is 1.788796

#write_xlsx(x = improvedMyData, path = "myData.xlsx", col_names = TRUE)
