package com.yanm.gol.logic.simulator;

import com.yanm.app.command.Command;
import com.yanm.gol.state.SimulatorState;

public interface SimulatorCommond extends Command<SimulatorState> {
    @Override
    void execute(SimulatorState simulatorState);

    @Override
    default Class<SimulatorState> getStateClass() {
        return SimulatorState.class;
    }
}
