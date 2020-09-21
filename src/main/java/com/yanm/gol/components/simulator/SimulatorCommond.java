package com.yanm.gol.components.simulator;

import com.yanm.app.command.Command;

public interface SimulatorCommond extends Command<SimulatorState> {
    @Override
    void execute(SimulatorState simulatorState);

    @Override
    default Class<SimulatorState> getStateClass() {
        return SimulatorState.class;
    }
}
