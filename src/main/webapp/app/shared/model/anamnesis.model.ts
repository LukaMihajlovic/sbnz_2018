import {IDiagnosis} from "./diagnosis.model";
import {IIngredient} from "./ingredient.model";
import {IDrug} from "./drug.model";


export interface IAnamnesis {
    id?: number;
    currentDiagnosisId?: number;
    diagnoses?: IDiagnosis[];
    allergiesIngredient?: IIngredient[];
    allergiesDrug?: IDrug[];
}

export class Anamnesis implements IAnamnesis {
    constructor(
        public id?: number,
        public currentDiagnosisId?: number,
        public diagnoses?: IDiagnosis[],
        public allergiesIngredient?: IIngredient[],
        public allergiesDrug?: IDrug[]
    ) {}
}
