package com.teamwizardry.librarianlib.features.gui.component.supporting

import com.teamwizardry.librarianlib.core.LibrarianLog
import com.teamwizardry.librarianlib.features.eventbus.Event
import com.teamwizardry.librarianlib.features.gui.component.GuiComponent
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.kotlin.component1
import com.teamwizardry.librarianlib.features.kotlin.component2
import com.teamwizardry.librarianlib.features.math.Vec2d
import no.birkett.kiwi.*

@Suppress("LEAKING_THIS", "UNUSED")
class ComponentLayoutHandler(val component: GuiComponent) {

    private val posX = Anchor<XAxis>(component, "posX")
    private val posY = Anchor<YAxis>(component, "posY")
    private val sizeX = Anchor<Dimension>(component, "sizeX")
    private val sizeY = Anchor<Dimension>(component, "sizeY")

    /** The anchor corresponding to the minimum x coordinate of the component's bounds */
    val left: LayoutExpression<XAxis>
        get() {
            val parentLayout = component.parent?.layout ?: return posX
            if(parentLayout.solver != null) return posX
            return fixAnchorExpression(parentLayout.left + posX, "left")
        }
    /** The anchor corresponding to the minimum y coordinate of the component's bounds */
    val top: LayoutExpression<YAxis>
        get() {
            val parentLayout = component.parent?.layout ?: return posY
            if(parentLayout.solver != null) return posY
            return fixAnchorExpression(parentLayout.top + posY, "left")
        }
    /** The anchor corresponding to the maximum x coordinate of the component's bounds */
    @Suppress("UNCHECKED_CAST")
    val right: LayoutExpression<XAxis>
        get() = fixAnchorExpression(left + (width as LayoutExpression<XAxis>), "right")
    /** The anchor corresponding to the maximum y coordinate of the component's bounds */
    @Suppress("UNCHECKED_CAST")
    val bottom: LayoutExpression<YAxis>
        get() = fixAnchorExpression(top + (height as LayoutExpression<YAxis>), "bottom")
    /** The anchor corresponding to the x coordinate of the center of the component's bounds */
    val centerX: LayoutExpression<XAxis>
        get() = fixAnchorExpression((left + right) / 2, "centerX")
    /** The anchor corresponding to the y coordinate of the center of the component's bounds */
    val centerY: LayoutExpression<YAxis>
        get() = fixAnchorExpression((top + bottom) / 2, "centerY")

    /** The anchor corresponding to the width of the component's bounds */
    val width: LayoutExpression<Dimension>
        get() = fixAnchorExpression(sizeX + 0, "width") // + 0 to make a new expression so I can rename it. I can't rename plain anchors.
    /** The anchor corresponding to the height of the component's bounds */
    val height: LayoutExpression<Dimension>
        get() = fixAnchorExpression(sizeY + 0, "height") // ditto.

    /**
     * If nonzero, the width and height constraints will be set to the component's implicit size if it exists, rather
     * than the component's size attribute, and use this strength
     *

     */
    var implicitSizeStrength = Strength.IMPLICIT

    /**
     * Calls the passed lambda when adding constraints. The lambda may be called multiple times if the component is
     * removed from its parent and added to another parent.
     */
    fun constraints(lambda: Runnable) {
        constraintCallbacks.add(lambda)

        if(containingSolver != null) {
            lambda.run()
        }
    }

    fun add(constraint: LayoutConstraint) {
        containingSolver?.addConstraint(constraint.kiwiConstraint)
    }

    fun remove(constraint: LayoutConstraint) {
        containingSolver?.removeConstraint(constraint.kiwiConstraint)
    }

    /** Specifies the strength with which the x coordinate defined by [GuiComponent.pos] should be maintained */
    var leftStay = Strength.NONE
    /** Specifies the strength with which the y coordinate defined by [GuiComponent.pos] should be maintained */
    var topStay = Strength.NONE
    /** Specifies the strength with which the width defined by [GuiComponent.size] should be maintained */
    var widthStay = Strength.NONE
        get() = if(baked) Strength.REQUIRED else field
    /** Specifies the strength with which the height defined by [GuiComponent.size] should be maintained */
    var heightStay = Strength.NONE
        get() = if(baked) Strength.REQUIRED else field

    /** Specifies the strength with which the size defined by [GuiComponent.size] should be maintained */
    var sizeStay
        get() = widthStay.min(heightStay)
        set(value) {
            widthStay = value
            heightStay = value
        }

    /** Specifies the strength with which the position defined by [GuiComponent.pos] should be maintained */
    var positionStay
        get() = leftStay.min(topStay)
        set(value) {
            leftStay = value
            topStay = value
        }

    /** Specifies the strength with which the size and position defined by [GuiComponent.size] and [GuiComponent.pos] should be maintained */
    var boundsStay
        get() = sizeStay.min(positionStay)
        set(value) {
            sizeStay = value
            positionStay = value
        }

    /**
     * Creates constraints to attach this component's top, left, bottom, and right anchors to the same anchors on
     * [other], adding the respective values in [topLeftPlus] and [bottomRightPlus] to [other]'s constraints.
     *
     * Adds the resulting constraints to this component.
     *
     * @return An array of the constraints added in the order `left, top, right, bottom`
     */
    @JvmOverloads
    fun boundsEqualTo(other: GuiComponent, topLeftPlus: Vec2d = Vec2d.ZERO, bottomRightPlus: Vec2d = Vec2d.ZERO): Array<LayoutConstraint> {
        return arrayOf(
                left.equalTo(other.layout.left + topLeftPlus.x),
                top.equalTo(other.layout.top + topLeftPlus.y),
                right.equalTo(other.layout.right + bottomRightPlus.x),
                bottom.equalTo(other.layout.bottom + bottomRightPlus.y)
        )
    }

    /**
     * Creates constraints to attach this component's top and left anchors to the same anchors on
     * [other], adding the respective values in [plus] to [other]'s constraints.
     *
     * Adds the resulting constraints to this component.
     *
     * @return An array of the constraints added in the order `left, top`
     */
    @JvmOverloads
    fun posEqualTo(other: GuiComponent, plus: Vec2d = Vec2d.ZERO): Array<LayoutConstraint> {
        return arrayOf(
                left.equalTo(other.layout.left + plus.x),
                top.equalTo(other.layout.top + plus.y)
        )
    }

    /**
     * Creates constraints to attach this component's width and height anchors to the same anchors on
     * [other], adding the respective values in [plus] to [other]'s constraints.
     *
     * Adds the resulting constraints to this component.
     *
     * @return An array of the constraints added in the order `width, height`
     */
    @JvmOverloads
    fun sizeEqualTo(other: GuiComponent, plus: Vec2d = Vec2d.ZERO): Array<LayoutConstraint> {
        return arrayOf(
                width.equalTo(other.layout.width + plus.x),
                height.equalTo(other.layout.height + plus.y)
        )
    }

    /**
     * Isolates
     */
    fun isolate() {
        if(component.relationships.components.isNotEmpty()) {
            throw IllegalStateException("Cannot isolate a component once it has children!")
        }
        if(component.relationships.parent != null) {
            throw IllegalStateException("Cannot isolate a component once it has been added to a component tree!")
        }
        val solver = Solver()
        this.solver = solver
        isolated = true

        setNeedsLayout()
    }

    /**
     * Applies the current layout if one exists, and disables autolayout for its children.
     *
     * If children have separate solver contexts designated with `isolate` they will function as normal.
     */
    fun bake() {
        needsLayout = true
        updateLayoutIfNeeded()
        solver = null
        baked = true
    }

    fun setNeedsLayout() {
        containingSolverComponent?.also {
            it.layout.needsLayout = true
        }
    }

    fun updateLayoutIfNeeded() {
        val solver = this.solver
        if(solver == null && !baked) {
            containingSolverComponent?.layout?.updateLayoutIfNeeded()
        } else if (needsLayout || solver?.changed == true) {

            fireLayoutEvent(GuiComponentEvents.PreLayoutEvent(component))

            solver?.also { solver ->
                addIntrinsic(solver)
                solver.updateVariables()
                update(solver)
            }

            fireLayoutEvent(GuiComponentEvents.PostLayoutEvent(component))

            needsLayout = false
            solver?.changed = false
        }
    }

    //region internals

    var isolated = false
        private set
    private val constraintCallbacks = mutableListOf<Runnable>()
    private var needsLayout = false
    private var baked = false

    internal var solver: Solver? = null
        set(value) {
            if(baked) throw IllegalStateException("Component already baked!")
            field = value
            if(value != null) {
                value.addConstraint(Symbolics.equals(posX.variable, 0.0))
                value.addConstraint(Symbolics.equals(posY.variable, 0.0))
            }
            onAddedToLayoutContext()
        }
    internal val constraints = mutableSetOf<LayoutConstraint>()

    internal val containingSolver: Solver?
        get() = component.parent?.layout?.solver ?: component.parent?.layout?.containingSolver
    internal val validSolvers: List<Solver>
        get() = listOf(solver, containingSolver).filterNotNull()
    internal val containingSolverComponent: GuiComponent?
        get() = if(component.parent?.layout?.solver != null) component.parent else component.parent?.layout?.containingSolverComponent

    internal fun removeComponent(component: GuiComponent) {
        val removed = mutableSetOf<GuiComponent>()

        removed.add(component)
        component.relationships.addChildrenRecursively(removed)

        val removedConstraints = constraints.filter { it.involvedComponents.any { it in removed } }
        constraints.removeAll(removedConstraints)
        removedConstraints.forEach {
            it.isActive = false
            it.valid = false
        }
    }

    private fun addIntrinsic(solver: Solver) {
        if(isolated) {
            if(solver == this.solver) {
                val implicit = component.getImplicitSize()
                if (implicitSizeStrength != Strength.NONE && implicit != null) {
                    solver.setEditVariable(sizeX.variable, implicit.x, implicitSizeStrength, "width")
                    solver.setEditVariable(sizeY.variable, implicit.y, implicitSizeStrength, "height")
                }
                if(widthStay > implicitSizeStrength || implicit == null) solver.setEditVariable(sizeX.variable, component.size.x, widthStay, "width")
                if(heightStay > implicitSizeStrength || implicit == null) solver.setEditVariable(sizeY.variable, component.size.y, heightStay, "height")
            } else {
                solver.setEditVariable(sizeX.variable, component.size.x, Strength.REQUIRED, "width")
                solver.setEditVariable(sizeY.variable, component.size.y, Strength.REQUIRED, "height")

                solver.setEditVariable(posX.variable, component.pos.x, leftStay, "posX")
                solver.setEditVariable(posY.variable, component.pos.y, topStay, "posY")
            }
        } else {
            val implicit = component.getImplicitSize()
            if (implicitSizeStrength != Strength.NONE && implicit != null) {
                solver.setEditVariable(sizeX.variable, implicit.x, implicitSizeStrength, "width")
                solver.setEditVariable(sizeY.variable, implicit.y, implicitSizeStrength, "height")
            }
            if(widthStay > implicitSizeStrength || implicit == null) solver.setEditVariable(sizeX.variable, component.size.x, widthStay, "width")
            if(heightStay > implicitSizeStrength || implicit == null) solver.setEditVariable(sizeY.variable, component.size.y, heightStay, "height")

            solver.setEditVariable(posX.variable, component.pos.x, leftStay, "posX")
            solver.setEditVariable(posY.variable, component.pos.y, topStay, "posY")
        }
        component.relationships.components.forEach {
            it.layout.addIntrinsic(solver)
        }
    }

    private fun update(solver: Solver) {
        if(solver != this.solver) {
            var (x, y) = component.pos
            if(solver.usesVariable(posX.variable)) x = posX.variable.value
            if(solver.usesVariable(posY.variable)) y = posY.variable.value
            component.pos = vec(x, y)
        }
        var (x, y) = component.size
        if(solver.usesVariable(sizeX.variable)) x = sizeX.variable.value
        if(solver.usesVariable(sizeY.variable)) y = sizeY.variable.value
        component.size = vec(x, y)

        if(baked && solver != this.solver) return

        component.relationships.components.forEach {
            it.layout.update(solver)
        }
    }

    init {
        component.BUS.hook(GuiComponentEvents.PostAddToParentEvent::class.java) { e ->
            if(solver == null && containingSolver != null)
                onAddedToLayoutContext()
        }
    }

    private fun onAddedToLayoutContext() {
        constraintCallbacks.forEach(Runnable::run)
        component.relationships.components.forEach {
            if(it.layout.solver != null || it.layout.baked) return@forEach
            it.layout.onAddedToLayoutContext()
        }
    }

    private fun fireLayoutEvent(event: Event) {
        component.BUS.fire(event)
        component.relationships.components.forEach {
            if(it.layout.solver != null || it.layout.baked) return@forEach
            it.layout.fireLayoutEvent(event)
        }
    }

    // Uses depth-first search so subcomponents with isolated layouts are updated first
    internal fun updateAllLayoutsIfNeeded() {
        component.relationships.components.forEach {
            it.layout.updateAllLayoutsIfNeeded()
        }
        if(solver != null || baked)
            updateLayoutIfNeeded()
    }

    fun <T: ExpressionMetric> fixAnchorExpression(expr: LayoutExpression<T>, name: String): LayoutExpression<T> {
        expr.stringRepresentation = "$component#$name"

        // otherwise the entire path from the root component to this is considered involved, which means often there
        // will be no common solver.
        expr.involvedComponents = setOf(component)
        return expr
    }

    /**
     * Called for every constraint added to this root, this method replaces this component's `left` and `right` anchors
     * with 0
     */
    internal fun adjustChildConstraint(constraint: Constraint): Constraint {
        val toRemove = left.kiwiExpression.terms.map { it.variable } +
                top.kiwiExpression.terms.map { it.variable }

        constraint.expression.terms.removeIf { it.variable in toRemove }

        return constraint
    }

    internal fun Solver.setEditVariable(variable: Variable, value: Double, strength: Strength, name: String) {
        if(!this.usesVariable(variable)) return

        var edit = editVariable(variable)

        if(strength == Strength.NONE) {
            if(edit != null) removeEditVariable(variable)
            return
        }

        if(edit?.constraint?.strength != strength) {
            if(edit != null) removeEditVariable(variable)
            try {
                addEditVariable(variable, strength, value)
            } catch (e: UnsatisfiableConstraintException) {
                val fullName = "$component#$name"
                LibrarianLog.error("Unsatisfiable edit variable: $fullName == $value strength: $strength")
                LibrarianLog.errorStackTrace(e)
                return
            }
        }
        edit = editVariable(variable)
        if(edit?.constant != value) {
            try {
                suggestValue(variable, value)
            } catch (e: UnsatisfiableConstraintException) {
                val fullName = "$component#$name"
                LibrarianLog.error("Unsatisfiable edit variable: $fullName == $value strength: $strength")
                LibrarianLog.errorStackTrace(e)
            }
        }
    }
    //endregion
}

