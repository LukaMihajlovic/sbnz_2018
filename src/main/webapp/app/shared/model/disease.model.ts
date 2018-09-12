
import {ISymptom} from "./symptom.model";
import {IDiagnosis} from "./diagnosis.model";

export interface IDisease {
    id?: number;
    name?: string;
    symptoms?: ISymptom[];
    diagnoses?: IDiagnosis[];
}

export class Disease implements IDisease {
    constructor(public id?: number, public name?: string, public symptoms?: ISymptom[], public diagnoses?: IDiagnosis[]) {}
}
