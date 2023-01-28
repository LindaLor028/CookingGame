import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
/**
 * This class generate a Comparer method that compare with a input Recipe and a template Recipe, 
 * return a score that represent the similarity between the input and template.
 */
public class RecipeComparer {

    // instance vars
    private double score; // score of the similarity between inputRecipe and templateRecipe 
    private Queue<String> input, template;

    private double ingredientAccuracy = 0.0; //Calculate how many ingredient in the input recipe are correct
    private double orderAccuracy = 0.0; //keep track on whether a correct ingredient is at the correct order
    private double wrongIngredientPenalty = 0.0; // counts points down if add wrong ingredient or more ingredient than needed


    /**
     * Constructor of the RecipeComparer.
     * A RecipeCompare should have an inputRecipe and an templateRecipe as input.
     * @param inputRecipe
     * @param templateRecipe
     */
    public RecipeComparer(Queue<String> inputRecipe, Queue<String> templateRecipe){
        input = inputRecipe;
        template = templateRecipe;

    }

    /**
     * This method compares the inputRecipe with the templateRecipe.
     * The score of the similarity are calculated as a sum of ingredient accuracy and order accuracy.
     * ingredientAccuracy counts how many ingredient in the inputRecipe matched with the ingredients in the templateRecipe.
     * orderAccuracy is sum of the length minus the differece between the indexes of macthed ingredients.
     * For example: inputRecipe: {a,c,b,e},templateRecipe: {a,b,c,d}
     * the inputeRecipe get 4 points for ingredientAccracy,
     * get (4-0)+(4-|3-2|)+(4-|2-3|)+0 = 4 + 3 + 3 + 0 = 10 points for orderAccuracy
     * 
     * After calculated the score, normalized the score to 100 scare by the normalizeScore() method and update the instance variable score
     */
    public void compareRecipes(){
        // check ingredient accuracy 1st 
        Iterator<String> itr = input.iterator();

        //Create map to keep track on duplicates
        Map<String,Integer> inputMap = new HashMap<>();
        Map<String,Integer> tempMap = checkDuplicates(template);

        int inputIndex = 0;

        // loop over the input recipe
        while(itr.hasNext()){
            String itrString = itr.next();
            double order = 0.0;

            // if template contains current ingredient
            if(tempMap.containsKey(itrString)){

                // if the correct ingredient is not alreay in the input map(added the first time)
                if(!inputMap.containsKey(itrString)){
                    order = findIngreidentInTemplate(itrString, inputIndex);
                    ingredientAccuracy += 1;
                    inputMap.put(itrString, 1);
                }
                else{
                    // if the number of this correct ingredients in the input recipe is less than it in the template
                    if(inputMap.get(itrString) < tempMap.get(itrString)){
                        order = findIngreidentInTemplate(itrString, inputIndex);
                        ingredientAccuracy += 1;
                    }
                    else{ // player add more ingredient than needed
                        wrongIngredientPenalty += 0.5;
                    }
                    inputMap.put(itrString,inputMap.get(itrString)+1);
                }
            }
            else{ // player add wrong ingredient
                wrongIngredientPenalty += 0.5;
            }

            orderAccuracy += order;

            //increment the inputIndex    
            inputIndex +=1;
        }

        // add up the ingredient accuracy and order accuracy
        score = ingredientAccuracy + orderAccuracy - wrongIngredientPenalty;

        // normalize the score
        score = normalizeScore(score, template.size());
    }

    /**
     * @return the score of the comparer
     */
    public double getScore(){
        return score;
    }


    /**
     * This function takes a recipe as input and return a map that records all the element in the queue as key and
     * number of times the element appears as the value.
     * @param recipe
     * @return
     */
    private Map<String,Integer> checkDuplicates(Queue<String> recipe){
        Map<String,Integer> result = new HashMap<>();

        for(String food:recipe){
            if(result.containsKey(food)){
                result.put(food, result.get(food)+1);
            }
            else{
                result.put(food, 1);
            }
        }

        return result;
    }

    /**
     * This function is used to find the correct ingredient in the template recipe, compare the difference between
     * index of that ingredient in the input recipe and template recipe, return the maximum value of template size 
     * minus difference in indexes as the order accuracy for that ingredient.
     * @param ingredient
     * @param inputIndex
     * @return
     */
    private double findIngreidentInTemplate(String ingredient, int inputIndex){

        Iterator<String> tempItr = template.iterator();
        int templateIndex = 0;
        double order = 0.0;

        while (tempItr.hasNext()&&templateIndex<=template.size()){

            String tempString = tempItr.next();
            if (ingredient.equals(tempString)){
                double thisorder = template.size() - Math.abs(inputIndex - templateIndex);
                if(order <= thisorder){
                    order = thisorder;
                }
            }
            // increment the templateIndex            
            templateIndex +=1;
        }

        return order;
    }

    /**
     * This function takes a score and the length of the template recipe as input,
     * dividing the score by the maximum of points a inputRecipe can get(which is equal to length * (lenght+1) ),
     * normalize the score to 100 scare by multiply it by 100.
     * @param score
     * @param length
     * @return
     */
    private double normalizeScore(double score, double length){

        // prevent negative score causing by wrong ingredient penalty
        if(score <= 0){
            score = 0;
        }

        double ss = (1+length)*length;
        double s = score/ss*100;

        s = Math.round(s*100)/100;

        return s;
    }

    /**
     * A standard toString method.
     */
    public String toString(){
        return "RecipeComparer Object";
    }
    
}
