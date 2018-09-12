
export interface ISymptom {
    id?: number;
    name?: string;
    low?: number;
    high?: number;
    spec?: boolean;
}

export class Symptom implements ISymptom {
    constructor(
        public id?: number,
        public name?: string,
        public low?: number,
        public high?: number,
        public spec?: boolean,
    ) {
        this.spec = false;
    }
}
