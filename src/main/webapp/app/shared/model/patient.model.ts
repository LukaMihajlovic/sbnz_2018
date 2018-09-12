
export interface IPatient {
    id?: number;
    firstname?: string;
    lastname?: string;
    anamnesisId?: number;
}

export class Patient implements IPatient {
    constructor(
        public id?: number,
        public firstname?: string,
        public lastname?: string,
        public anamnesisId?: number
    ) {}
}
