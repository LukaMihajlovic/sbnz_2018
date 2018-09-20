package rs.ac.uns.ftn.sbnz.domain;


import java.util.List;

public class AllergiesToClient {

    public List<Drug> drugs;
    public List<Ingredient> ingredients;

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public List<Ingredient> getCriticalIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> criticalIngredients) {
        this.ingredients = criticalIngredients;
    }
}
