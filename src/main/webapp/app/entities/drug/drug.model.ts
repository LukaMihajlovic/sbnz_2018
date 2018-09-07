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
        public anamneses?: BaseEntity[],
    ) {
    }
}
