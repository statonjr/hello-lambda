import { loadFile } from 'nbb';

const { handler } = await loadFile('./app.cljs');

export { handler }