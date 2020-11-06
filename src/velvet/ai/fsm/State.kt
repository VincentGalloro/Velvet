package velvet.ai.fsm

/*

val playingBrain = FSM()

...


val brain = FSM()

val searching = brain.createStatelessState("searching")
val playing = brain.createStatefulState<BoardLocation>("playing")

playing.onStateEnter = { playingBrain.start(playingSubState, it) }
playing.onStateExit = { playingBrain.stop() }

val boardLocator = BoardLocator()
searching.addTransition(playing, { boardLocator.search() }, { it != null })
playing.addTransition(searching, { !boardLocator.verifyLocation(it) })

brain.start()

 */