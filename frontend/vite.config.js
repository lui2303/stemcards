export default {
    optimizeDeps: {
        include: ['canvas2svg']
      },
    build: {
      rollupOptions: {
        external: ['canvas2svg'],
        input: {
          main: 'create_flashcard.html',
          about: './dist/index.html',
        }
      }
    }
  };