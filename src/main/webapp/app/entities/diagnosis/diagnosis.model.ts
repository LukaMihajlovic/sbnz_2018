import { BaseEntity } from './../../shared';

export class Diagnosis implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public recovery?: boolean,
        public doctor?: BaseEntity,
    ) {
        this.recovery = false;
    }
}
