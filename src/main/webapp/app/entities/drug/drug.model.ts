import { BaseEntity } from './../../shared';

export const enum DrugType {
    'ANTIBIOTIC',
    'ANALGETIC',
    'OTHER'
}

export class Drug implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: DrugType,
        public ingredients?: BaseEntity[],
        public anamneses?: BaseEntity[],
        public diagnoses?: BaseEntity[],
    ) {
    }
}
