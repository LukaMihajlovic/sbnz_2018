


import {IDiagnosis} from "./diagnosis.model";

export interface IDoctor {
    id?: number;
    userId?: number;
    diagnoses?: IDiagnosis[];
}

export class Doctor implements IDoctor {
    constructor(public id?: number, public userId?: number, public diagnoses?: IDiagnosis[]) {}
}
