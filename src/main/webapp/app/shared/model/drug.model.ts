

import {IIngredient} from "./ingredient.model";
import {IAnamnesis} from "./anamnesis.model";
import {IDiagnosis} from "./diagnosis.model";

export const enum MedicationType {
    ANTIBIOTIC = 'ANTIBIOTIC',
    ANALGETIC = 'ANALGETIC',
    OTHER = 'OTHER'
}

export interface IDrug {
    id?: number;
    name?: string;
    type?: MedicationType;
    anamneses?: IAnamnesis[];
    diagnoses?: IDiagnosis[];
    ingredients?: IIngredient[];
}

export class Medication implements IDrug {
    constructor(
        public id?: number,
        public name?: string,
        public type?: MedicationType,
        public ingredients?: IIngredient[],
        public anamneses?: IAnamnesis[],
        public diagnoses?: IDiagnosis[]
    ) {}
}
