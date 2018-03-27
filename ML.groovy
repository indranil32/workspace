// test data set
Map<Integer,Double[]> testData = new HashMap<Integer,Double[]>();
Double[] set = new Double[5]
set[0] = 1
set[1] = 90 // size
set[2] = 2 // number of rooms
set[3] = 23 // age of house
set[4] = 249000 // price
testData.put(1, set)

set = new Double[5]
set[0] = 1
set[1] = 101 // size
set[2] = 3 // number of rooms
set[3] = 0 // age of house
set[4] = 338000 // price
testData.put(2, set)

set = new Double[5]
set[0] = 1
set[1] = 1330 // size
set[2] = 11 // number of rooms
set[3] = 12 // age of house
set[4] = 6500000 // price
testData.put(3, set)

//set = new Double[4]
//set[0] = 90 // size
//set[1] = 2 // number of rooms
//set[2] = 23 // age of house
//set[3] = 249000 // price
//realData.put(4, set)

public class LinearRegressionFunction {
    private final Double[] thetaVector;
    
    public LinearRegressionFunction(Double[] thetaVector) {
        this.thetaVector = Arrays.copyOf(thetaVector, thetaVector.length)
    }
    
    public Double apply(Double[] featureVector) {
        assert featureVector[0] == 1.0;
        double predection = 0.0
        // liner equation
        // thetaVector[0]*1 + thetaVector[1] *  featureVector[1] + ...
        for (int i = 0 ; i < thetaVector.length ; i++) {
            predection += thetaVector[i] * featureVector[i];
        }
        return predection
    }
    
    public double[] getThetas() {
      return Arrays.copyOf(thetaVector, thetaVector.length);
    }
    
}

//Double[] thetaVector = new Double[] { 1.004579, 5.286822 }
Double[] thetaVector = new Double [2];
thetaVector[0] = new Double(1.004579f)
thetaVector[1] = new Double(5.286822f)

LinearRegressionFunction targetFunction = new LinearRegressionFunction(thetaVector);

//Double[] featureVector = new Double[2];// { 1.0, 1330.0 };
//featureVector[0] = new Double(1.0f)
//featureVector[1] = new Double(1330.0f)

// make the prediction
//double predictedPrice = targetFunction.apply(featureVector);
//println predictedPrice
double sumSquaredErrors = 0;

for (int key : testData.keySet()) {
    Double [] featureVector =  testData.get(key);
    // create the feature vector function with x0=1 (for computational reasons) and x1=house-size
    //Double[] featureVector = new Double[2];// { 1.0, 1330.0 };
    //featureVector[0] = new Double(1.0f)
    //featureVector[1] = new Double(1330.0f)

    // make the prediction
    double predictedPrice = targetFunction.apply(featureVector);
    double label = featureVector[4]/1000
    double gap = predictedPrice - label;
    sumSquaredErrors += Math.pow(gap, 2);
    println label
    println predictedPrice 
    println sumSquaredErrors
     
}

def a = (1.0 / (2 * 3)) * sumSquaredErrors;

println a




