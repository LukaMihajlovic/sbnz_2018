
import {IAnamnesis} from "./anamnesis.model";
import {IDrug} from "./drug.model";

export interface IIngredient {
    id?: number;
    name?: string;
    drugs?: IDrug[];
    anamneses?: IAnamnesis[];
}

export class Ingredient implements IIngredient {
    constructor(public id?: number, public name?: string, public drugs?: IDrug[], public anamneses?: IAnamnesis[]) {}
}
