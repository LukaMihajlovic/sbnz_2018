
import {IAnamnesis} from "./anamnesis.model";
import {IDiagnosis} from "./diagnosis.model";
import {IIngredient} from "./ingredient.model";

export const enum DrugType {
    'ANTIBIOTIC',
    'ANALGETIC',
    'OTHER'
}
export interface IDrug {
    id?: number;
    name?: string;
    type?: DrugType;
    anamneses?: IAnamnesis[];
    diagnoses?: IDiagnosis[];
    ingredients?: IIngredient[];
}

export class Drug implements IDrug {
    constructor(
        public id?: number,
        public name?: string,
        public type?: DrugType,
        public ingredients?: IAnamnesis[],
        public anamneses?: IDiagnosis[],
        public diagnoses?: IIngredient[],
    ) {
    }
}
