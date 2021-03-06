import { BaseEntity } from './../../shared';

export class Diagnosis implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public recovery?: boolean,
        public doctor?: BaseEntity,
        public anamnesis?: BaseEntity,
        public drugs?: BaseEntity[],
        public symptoms?: BaseEntity[],
        public diseases?: BaseEntity[],
    ) {
        this.recovery = false;
    }
}
