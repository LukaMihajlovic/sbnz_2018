
import {IDiagnosis} from "./diagnosis.model";
import {IDisease} from "./disease.model";

export interface ISymptom {
    id?: number;
    name?: string;
    low?: number;
    high?: number;
    diseases?: IDisease[];
    spec?: boolean;
    diagnoses?: IDiagnosis[];
}


export class Symptom implements ISymptom {
    constructor(
        public id?: number,
        public name?: string,
        public low?: number,
        public high?: number,
        public spec?: boolean,
        public diseases?: IDisease[],
        public diagnoses?: IDiagnosis[]
    ) {
        this.spec = false;
    }
}
