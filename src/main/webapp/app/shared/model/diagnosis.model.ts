import { Moment } from 'moment';
import {ISymptom} from "./symptom.model";
import {IDrug} from "./drug.model";
import {IDisease} from "./disease.model";

export interface IDiagnosis {
    id?: number;
    date?: Moment;
    recovery?: boolean;
    doctorId?: number;
    anamnesisId?: number;
    drugs?: IDrug[];
    symptoms?: ISymptom[];
    diseases?: IDisease[];
}

export class Diagnosis implements IDiagnosis {
    constructor(
        public id?: number,
        public date?: Moment,
        public recovery?: boolean,
        public doctorId?: number,
        public anamnesisId?: number,
        public drugs?: IDrug[],
        public symptoms?: ISymptom[],
        public diseases?: IDisease[]
    ) {
        this.recovery = false;
    }
}
