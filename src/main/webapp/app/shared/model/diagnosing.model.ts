


import {Symptom} from "../../entities/symptom/symptom.model";

export interface Diagnosing {
    symptoms?: Symptom[];
    anamnesisId?: number;
    diseases?: any;
}
